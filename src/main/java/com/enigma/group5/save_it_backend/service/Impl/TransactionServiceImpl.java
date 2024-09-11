package com.enigma.group5.save_it_backend.service.Impl;

import com.enigma.group5.save_it_backend.dto.request.SearchTransactionRequest;
import com.enigma.group5.save_it_backend.dto.request.TransactionRequest;
import com.enigma.group5.save_it_backend.dto.request.UpdatePaymentStatusRequest;
import com.enigma.group5.save_it_backend.dto.request.UpdateStocksVendorProductRequest;
import com.enigma.group5.save_it_backend.dto.response.PaymentResponse;
import com.enigma.group5.save_it_backend.dto.response.TransactionDetailResponse;
import com.enigma.group5.save_it_backend.dto.response.TransactionResponse;
import com.enigma.group5.save_it_backend.entity.*;
import com.enigma.group5.save_it_backend.repository.TransactionRepository;
import com.enigma.group5.save_it_backend.service.*;
import com.enigma.group5.save_it_backend.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final VendorService vendorService;
    private final VendorProductService vendorProductService;
    private final TransactionDetailService transactionDetailService;
    private final TransactionProductService transactionProductService;
    private final ReportService reportService;
    private final ProductService productService;
    private final CustomerService customerService;
    private final PaymentService paymentService;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionResponse create(TransactionRequest request) {

        //validation
        validationUtil.validate(request);

        //save to transaction
        Transaction transaction = Transaction.builder()
                .customer(customerService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName()))
                .transactionDate(new Date())
                .build();
        transactionRepository.saveAndFlush(transaction);

        //save to Transaction Detail
        List<TransactionDetail> transactionDetails = request.getTransactionDetail().stream().map(details -> {

            //validation
            validationUtil.validate(details);

            //Check if vendor product is existed and updateStocks stocks
            VendorProduct vendorProduct = vendorProductService.getById(details.getVendorProductId());
            vendorProductService.updateStocks(UpdateStocksVendorProductRequest.builder()
                    .vendorProductId(vendorProduct.getId())
                    .quantity(details.getQuantity())
                    .build()
            );

            return TransactionDetail.builder()
                    .transaction(transaction)
                    .vendorProduct(vendorProduct)
                    .quantity(details.getQuantity())
                    .build();
        }).toList();

        transactionDetailService.createBulk(transactionDetails);
        transaction.setTransactionDetails(transactionDetails);

        //save to payment
        Payment payment = paymentService.createPayment(transaction);
        transaction.setPayment(payment);

        //save to report
        reportService.createReport(transaction);

        //Transaction Response when success
        List<TransactionDetailResponse> transactionDetailResponses = transactionDetails.stream().map(
                detailResponse -> {

                    Product productById = productService.getById(detailResponse.getVendorProduct().getProduct().getId());
                    VendorProduct vendorProduct = vendorProductService.getById(detailResponse.getVendorProduct().getId());

                    return TransactionDetailResponse.builder()
                            .vendorProductName(vendorService.getById(vendorProduct.getVendor().getId()).getVendorName())
                            .productName(productById.getProductName())
                            .price(vendorProduct.getPrice())
                            .quantity(detailResponse.getQuantity())
                            .build();
                }).toList();

        PaymentResponse paymentResponse = PaymentResponse.builder()
                .id(payment.getId())
                .token(payment.getToken())
                .redirectUrl(payment.getRedirectUrl())
                .transactionStatus(payment.getTransactionStatus())
                .build();

        return TransactionResponse.builder()
                .id(transaction.getId())
                .username(SecurityContextHolder.getContext().getAuthentication().getName())
                .customerEmail(customerService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getEmailCustomer())
                .transactionDate(transaction.getTransactionDate())
                .transactionDetailResponses(transactionDetailResponses)
                .paymentResponse(paymentResponse)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TransactionResponse> getAll(SearchTransactionRequest searchTransactionRequest) {

        if(searchTransactionRequest.getPage() <= 0) {
            searchTransactionRequest.setPage(1);
        }

        String validSortBy;
        if("customerId".equalsIgnoreCase(searchTransactionRequest.getSortBy()) || "date".equalsIgnoreCase(searchTransactionRequest.getSortBy())){

            validSortBy = searchTransactionRequest.getSortBy();

        } else {

            validSortBy = "customerId";

        }

        Sort sort = Sort.by(Sort.Direction.fromString(searchTransactionRequest.getDirection()), validSortBy);

        Pageable pageable = PageRequest.of((searchTransactionRequest.getPage() - 1), searchTransactionRequest.getSize(), sort);

        List<Transaction> transactions = transactionRepository.findAll();

        List<TransactionResponse> getAllTransaction = transactions.stream().map(
                transaction -> {
                    List<TransactionDetailResponse> transactionDetailResponses = transaction.getTransactionDetails().stream().map(
                            detail -> {
                                Product product = productService.getById(detail.getVendorProduct().getProduct().getId());
                                VendorProduct vendorProduct = vendorProductService.getById(detail.getVendorProduct().getId());

                                return TransactionDetailResponse.builder()
                                        .vendorProductName(vendorService.getById(vendorProduct.getVendor().getId()).getVendorName())
                                        .productName(product.getProductName())
                                        .price(vendorProduct.getPrice())
                                        .quantity(detail.getQuantity())
                                        .build();
                            }).toList();

                    return TransactionResponse.builder()
                            .id(transaction.getId())
                            .username(transaction.getCustomer().getUserAccount().getUsername())
                            .customerEmail(transaction.getCustomer().getEmailCustomer())
                            .transactionDate(transaction.getTransactionDate())
                            .transactionDetailResponses(transactionDetailResponses)
                            .build();
                }).toList();



        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), getAllTransaction.size());

        List<TransactionResponse> pageContent = getAllTransaction.subList(start, end);

        return new PageImpl<>(pageContent, pageable, getAllTransaction.size());
    }

    @Transactional(readOnly = true)
    @Override
    public List<Transaction> getAllByCustomer(Customer customer) {
        return transactionRepository.findAllByCustomerId(customer.getId()).orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatus(UpdatePaymentStatusRequest request) {

        System.out.println("Panggil Akuuuuuuu");

        Transaction transaction = transactionRepository.findById(request.getOrderId())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found")
                );

        //save to transactionProduct
        if (request.getStatus().equalsIgnoreCase("settlement") && request.getFraudStatus().equalsIgnoreCase("accept")) {
            Customer customer = customerService.getById(transaction.getCustomer().getId());
            List<Transaction> transactionsOccurred = getAllByCustomer(customer);
            transactionProductService.create(transaction, transactionsOccurred);
        }

        Payment payment = transaction.getPayment();
        payment.setTransactionStatus(request.getStatus());
    }

}
