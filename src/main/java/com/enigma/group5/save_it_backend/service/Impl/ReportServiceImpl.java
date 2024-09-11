package com.enigma.group5.save_it_backend.service.Impl;


import com.enigma.group5.save_it_backend.dto.request.SearchReportRequest;
import com.enigma.group5.save_it_backend.dto.response.*;
import com.enigma.group5.save_it_backend.entity.*;
import com.enigma.group5.save_it_backend.entity.Report;
import com.enigma.group5.save_it_backend.repository.ReportRepository;
import com.enigma.group5.save_it_backend.service.*;
import com.enigma.group5.save_it_backend.specification.ReportSpecification;
import com.enigma.group5.save_it_backend.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final VendorProductService vendorProductService;
    private final ProductService productService;
    private final CustomerService customerService;

    private final ApplicationContext applicationContext;

    private final ValidationUtil validationUtil;

    @Transactional(readOnly = true)
    @Override
    public Page<Report> getAll(SearchReportRequest searchReportRequest) {
        if (searchReportRequest.getPage() <= 0){
            searchReportRequest.setPage(1);
        }
        String validSortBy;
        if ("transDate".equalsIgnoreCase(searchReportRequest.getSortBy())){
            validSortBy = searchReportRequest.getSortBy();
        }else {
            validSortBy = "transDate";
        }

        Sort sort = Sort.by(Sort.Direction.fromString(searchReportRequest.getDirection()), validSortBy);

        Pageable pageable = PageRequest.of((searchReportRequest.getPage() -1), searchReportRequest.getSize(), sort);

        Specification<Report> specification = ReportSpecification.getSpecification(searchReportRequest);

        return reportRepository.findAll(specification, pageable);
    }

    @Override
    public Page<ReportResponse> getAllReportBasedOnCustomer(SearchReportRequest searchReportRequest) {

        if (searchReportRequest.getPage() <= 0){
            searchReportRequest.setPage(1);
        }
        String validSortBy;
        if ("transactionDate".equalsIgnoreCase(searchReportRequest.getSortBy())){
            validSortBy = searchReportRequest.getSortBy();
        }else {
            validSortBy = "transactionDate";
        }

        Sort sort = Sort.by(Sort.Direction.fromString(searchReportRequest.getDirection()), validSortBy);

        Pageable pageable = PageRequest.of((searchReportRequest.getPage() -1), searchReportRequest.getSize(), sort);

        Specification<Report> specification = ReportSpecification.getSpecification(searchReportRequest);

        Customer customer = customerService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        List<Transaction> transactions = transactionService().getAllByCustomer(customer);

        List<Report> reportsBefore = reportRepository.findAll(specification).stream()
                .filter(report -> transactions.stream()
                        .anyMatch(transaction -> transaction.getId().equals(report.getTransaction().getId())))
                .collect(Collectors.toList());

        List<Report> reports = reportsBefore.stream().collect(Collectors.toMap(
                        rp -> rp.getTransaction().getTransactionDate(),
                        report -> report,
                        (existing, replacement) -> existing))
                .values()
                .stream()
                .sorted((a,b) -> b.getTransaction().getTransactionDate().compareTo(a.getTransaction().getTransactionDate()))
                .collect(Collectors.toList());

        List<ReportResponse> reportResponses = reports.stream().map(
                report -> {
                    List<ItemReportResponses> items = report.getTransaction().getTransactionDetails().stream().map(
                            item -> {
                                ProductResponse product = ProductResponse.builder()
                                        .id(item.getVendorProduct().getProduct().getId())
                                        .name(item.getVendorProduct().getProduct().getProductName())
                                        .category(item.getVendorProduct().getProduct().getProductCategory().getCategoryName())
                                        .description(item.getVendorProduct().getProduct().getProductDescription())
                                        .image(ImageResponse.builder()
                                                .name(item.getVendorProduct().getProduct().getProductImage() != null ? item.getVendorProduct().getProduct().getProductImage().getName() : null)
                                                .url(item.getVendorProduct().getProduct().getProductImage() != null ? item.getVendorProduct().getProduct().getProductImage().getPath(): null)
                                                .build())
                                        .build();

                                VendorResponse vendorResponse = VendorResponse.builder()
                                        .vendorId(item.getVendorProduct().getVendor().getId())
                                        .vendorName(item.getVendorProduct().getVendor().getVendorName())
                                        .vendorEmail(item.getVendorProduct().getVendor().getVendorEmail())
                                        .vendorPhone(item.getVendorProduct().getVendor().getVendorPhone())
                                        .vendorAddress(item.getVendorProduct().getVendor().getVendorAddress())
                                        .build();

                                return ItemReportResponses.builder()
                                        .productDetails(product)
                                        .vendorDetails(vendorResponse)
                                        .price(item.getVendorProduct().getPrice())
                                        .quantity(item.getQuantity())
                                        .totalPricePerItem(report.getTotal())
                                        .build();
                            }
                    ).toList();

                    Integer totalQuantity = items.stream().reduce(0, (a, b) -> a + b.getQuantity(), Integer::sum);
                    Long totalPrice = items.stream().reduce(0L, (a, b) -> a + b.getTotalPricePerItem(), Long::sum);

                    return ReportResponse.builder()
                            .transactionId(report.getTransaction().getId())
                            .transactionDate(report.getTransDate())
                            .transactionTime(report.getTransaction().getTransactionDate().getHours() + ":" + report.getTransaction().getTransactionDate().getMinutes() + ":" + report.getTransaction().getTransactionDate().getSeconds())
                            .paymentToken(report.getTransaction().getPayment() != null ? report.getTransaction().getPayment().getToken() : null)
                            .redirectUrl(report.getTransaction().getPayment() != null ? report.getTransaction().getPayment().getRedirectUrl() : null)
                            .paymentStatus(report.getTransaction().getPayment() != null ? report.getTransaction().getPayment().getTransactionStatus() : null)
                            .username(report.getTransaction().getCustomer().getUserAccount().getUsername())
                            .customerEmail(report.getTransaction().getCustomer().getEmailCustomer())
                            .items(items)
                            .totalQuantity(totalQuantity)
                            .totalPrice(totalPrice)
                            .build();
                }
        ).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), reportResponses.size());

        List<ReportResponse> pageContent = reportResponses.subList(start, end);

        return new PageImpl<>(pageContent, pageable, reportResponses.size());

    }

    @Transactional(readOnly = true)
    public List<Report> getReportsBetweenDate(Date startDate, Date endDate) {

        return reportRepository.findByTransDateBetween(startDate, endDate);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Report> createReport(Transaction transaction) {

        List<Report> reports = transaction.getTransactionDetails().stream().map(
                reportRequest -> {

                    VendorProduct vendorProduct = vendorProductService.getById(reportRequest.getVendorProduct().getId());
                    Product product = productService.getById(vendorProduct.getProduct().getId());

                    return Report.builder()
                            .transaction(transaction)
                            .id(vendorProduct.getId())
                            .transDate(transaction.getTransactionDate())
                            .productName(product.getProductName())
                            .vendorName(vendorProduct.getVendor().getVendorName())
                            .category(vendorProduct.getProduct().getProductCategory().getCategoryName())
                            .price(vendorProduct.getPrice())
                            .quantity(reportRequest.getQuantity())
                            .total(vendorProduct.getPrice() * reportRequest.getQuantity())
                            .build();
                }
        ).toList();

        return reportRepository.saveAllAndFlush(reports);
    }

    @Override
    public void exportToCSV(Page<Report> reports) {
        try (FileWriter writer = new FileWriter("report.csv")){
            writer.append("Transaction Date, Product Name, Price, Quantity, Total, TransactionId, ");
            for (Report report : reports) {
                        writer.append(report.getTransDate().toString()).append(",").
                        append(report.getProductName()).append(",").
                        append(report.getPrice().toString()).append(",").
                        append(report.getQuantity().toString()).append(",").
                        append(report.getTotal().toString()).append(",").
                        append(report.getTransaction().getId()).append(",");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TransactionService transactionService() {
        return applicationContext.getBean(TransactionService.class);
    }
}
