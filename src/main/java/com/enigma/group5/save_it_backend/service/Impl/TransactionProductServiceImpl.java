package com.enigma.group5.save_it_backend.service.Impl;

import com.enigma.group5.save_it_backend.constant.APIUrl;
import com.enigma.group5.save_it_backend.dto.request.SearchTransactionProductRequest;
import com.enigma.group5.save_it_backend.dto.request.TransactionProductUpdateRequest;
import com.enigma.group5.save_it_backend.dto.response.ImageResponse;
import com.enigma.group5.save_it_backend.dto.response.TransactionProductResponse;
import com.enigma.group5.save_it_backend.dto.response.TransactionProductsGoodsResponse;
import com.enigma.group5.save_it_backend.dto.response.VendorProductResponse;
import com.enigma.group5.save_it_backend.entity.*;
import com.enigma.group5.save_it_backend.repository.TransactionProductRepository;
import com.enigma.group5.save_it_backend.service.*;
import com.enigma.group5.save_it_backend.service.VendorProductService;
import com.enigma.group5.save_it_backend.specification.TransactionProductSpecification;
import com.enigma.group5.save_it_backend.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionProductServiceImpl implements TransactionProductService {
    private final TransactionProductRepository transactionProductRepository;
    private final CustomerService customerService;
    private final ProductService productService;
    private final VendorProductService vendorProductService;
    private final ImageService imageService;
    private final ApplicationContext applicationContext;

    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<TransactionProduct> create(Transaction transactionRequest, List<Transaction> transactionsOccurred) {

        validationUtil.validate(transactionRequest);
        List<TransactionDetail> transactionDetails = transactionRequest.getTransactionDetails();

        List<TransactionProduct> transactionProducts = new ArrayList<>();

        AtomicReference<Boolean> flag = new AtomicReference<>(false);

        List<TransactionProduct> getAllByTransaction = getAllByTransaction(transactionsOccurred);

        if (getAllByTransaction == null) {
            Warehouse warehouse = getWarehouseService().create(Warehouse.builder()
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .build());
            transactionDetails.stream().map(
                    transactionDetailList -> {
                        TransactionProduct transactionProduct = TransactionProduct.builder()
                                .transaction(transactionDetailList.getTransaction())
                                .vendorProductId(transactionDetailList.getVendorProduct().getId())
                                .warehouse(warehouse)
                                .price(transactionDetailList.getVendorProduct().getPrice())
                                .stock(transactionDetailList.getQuantity())
                                .createdAt(new Date())
                                .updatedAt(new Date())
                                .build();
                        transactionProducts.add(transactionProduct);
                        return transactionProducts;
                    }).toList();
        } else {
            //Check if vendor product is existed and updateStocks stocks
            transactionDetails.stream().map(
                    transactionDetailList -> {

                        TransactionProduct findByVpId = getByVendorProductFromList(getAllByTransaction, transactionDetailList.getVendorProduct().getId());

                        //if vendor product in warehouse doesn't exist or price is different
                        if (findByVpId == null || findByVpId.getPrice().longValue() != transactionDetailList.getVendorProduct().getPrice().longValue() && findByVpId != null) {
                            TransactionProduct transactionProduct = TransactionProduct.builder()
                                    .transaction(transactionDetailList.getTransaction())
                                    .vendorProductId(transactionDetailList.getVendorProduct().getId())
                                    .price(transactionDetailList.getVendorProduct().getPrice())
                                    .stock(transactionDetailList.getQuantity())
                                    .createdAt(new Date())
                                    .updatedAt(new Date())
                                    .build();
                            flag.set(true);
                            transactionProducts.add(transactionProduct);

                            //if vendor product in warehouse exist
                        } else if (findByVpId != null) {
                            findByVpId.setStock(findByVpId.getStock() + transactionDetailList.getQuantity());
                            findByVpId.setUpdatedAt(new Date());
                            transactionProducts.add(findByVpId);

                        }
                        return transactionProducts;
                    }).toList();

            if (flag.get()) {
                //save to warehouse
                Warehouse warehouse = Warehouse.builder()
                        .createdAt(new Date())
                        .updatedAt(new Date())
                        .build();
                getWarehouseService().create(warehouse);

                transactionProducts.stream().forEach(transactionProduct -> {
                    if (transactionProduct.getWarehouse() == null) {
                        transactionProduct.setWarehouse(warehouse);
                    }});
            }
        }

      return transactionProductRepository.saveAllAndFlush(transactionProducts);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TransactionProductResponse> getAll(SearchTransactionProductRequest searchTransactionProductRequest) {
        if (searchTransactionProductRequest.getPage() <= 0){
            searchTransactionProductRequest.setPage(1);
        }

        String validSortBy;
        if ("username".equalsIgnoreCase(searchTransactionProductRequest.getSortBy()) || "customerEmail".equalsIgnoreCase(searchTransactionProductRequest.getSortBy())
                || "createdAt".equals(searchTransactionProductRequest.getSortBy())) {
            validSortBy = searchTransactionProductRequest.getSortBy();
        }
        else {
            validSortBy = "createdAt";
        }

        Sort sort = Sort.by(Sort.Direction.fromString(searchTransactionProductRequest.getDirection()), validSortBy);

        Pageable pageable = PageRequest.of(searchTransactionProductRequest.getPage() - 1, searchTransactionProductRequest.getSize(), sort);

        Specification<TransactionProduct> specification = TransactionProductSpecification.getSpecification(searchTransactionProductRequest);

        List<TransactionProductResponse> transactionProducts = transactionProductRepository.findAll(specification).stream().map(
                warehouse -> parseToTransactionProductResponse(warehouse)
        ).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), transactionProducts.size());

        List<TransactionProductResponse> pageContent = transactionProducts.subList(start, end);

        return new PageImpl<>(pageContent, pageable, transactionProducts.size());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TransactionProductResponse> getAllByCustomer(SearchTransactionProductRequest searchTransactionProductRequest) {

        if (searchTransactionProductRequest.getPage() <= 0){
            searchTransactionProductRequest.setPage(1);
        }

        String validSortBy;
        if ("price".equalsIgnoreCase(searchTransactionProductRequest.getSortBy()) || "stocks".equalsIgnoreCase(searchTransactionProductRequest.getSortBy())
                || "createdAt".equals(searchTransactionProductRequest.getSortBy())) {
            validSortBy = searchTransactionProductRequest.getSortBy();
        }
        else {
            validSortBy = "createdAt";
        }

        Sort sort = Sort.by(Sort.Direction.fromString(searchTransactionProductRequest.getDirection()), validSortBy);

        Pageable pageable = PageRequest.of(searchTransactionProductRequest.getPage() - 1, searchTransactionProductRequest.getSize(), sort);

        Specification<TransactionProduct> specification = TransactionProductSpecification.getSpecification(searchTransactionProductRequest);

        Customer customer = customerService.getByUsername(searchTransactionProductRequest.getUsername());

        List<TransactionProduct> getAllData = transactionProductRepository.findAll(specification);

        List<TransactionProduct> getAllBasedOnCustomer = new ArrayList<>();

        getAllData.stream().forEach(transactionProduct -> {
            if(transactionProduct.getTransaction().getCustomer().getId().equals(customer.getId())) {
                getAllBasedOnCustomer.add(transactionProduct);
            }
        });

        List<TransactionProductResponse> transactionProductResponses = getAllBasedOnCustomer.stream().map(
                transactionProduct -> parseToTransactionProductResponse(transactionProduct)
        ).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), transactionProductResponses.size());

        List<TransactionProductResponse> pageContent = transactionProductResponses.subList(start, end);

        return new PageImpl<>(pageContent, pageable, transactionProductResponses.size());
    }

    @Transactional(readOnly = true)
    public TransactionProduct findByIdOrThrowNotFound(String id) {
        return transactionProductRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public TransactionProduct getById(String id) {
        Optional<TransactionProduct> transactionProduct = transactionProductRepository.findById(id);
        return transactionProduct.orElseThrow(() -> new RuntimeException("Item not found in transaction product"));
    }

    @Override
    public List<TransactionProduct> getByTransactionId(String transactionId) {
        return transactionProductRepository.findAllByTransactionId(transactionId).get();
    }

    @Transactional(readOnly = true)
    @Override
    public TransactionProduct findByVendorProductId(String vpId) {
        List<TransactionProduct> transactionProducts = transactionProductRepository.findAllByVendorProductId(vpId).get();

        if (transactionProducts.isEmpty()) {
            return null;
        } else {
            return transactionProducts.get(transactionProducts.size() - 1);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionProductResponse update(TransactionProductUpdateRequest transactionProductUpdateRequest) {

        TransactionProduct currentTransactionProduct = getById(transactionProductUpdateRequest.getTransactionProductId());

        if (!currentTransactionProduct.getTransaction().getCustomer().getUserAccount().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            throw new RuntimeException("You are not allowed to update this transaction product");
        }

        if (transactionProductUpdateRequest.getPrice() != null) {
            currentTransactionProduct.setPrice(transactionProductUpdateRequest.getPrice());
        }

        currentTransactionProduct.setUpdatedAt(new Date());

        transactionProductRepository.saveAndFlush(currentTransactionProduct);

        TransactionProductResponse transactionProductResponse = parseToTransactionProductResponse(currentTransactionProduct);

        return transactionProductResponse;
    }

    @Override
    public Long countByCustomer(Customer customer) {

        Long count = transactionProductRepository.findAll().stream()
                .filter(
                transactionProduct -> transactionProduct.getTransaction().getCustomer().getId().equals(customer.getId()))
                .reduce(0L, (subtotal, element) -> subtotal + element.getStock(), Long::sum);

        return count;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        TransactionProduct transactionProduct = getById(id);
        if(!transactionProduct.getTransaction().getCustomer().getUserAccount().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            throw new RuntimeException("You are not allowed to delete this transaction product");
        }
        transactionProductRepository.delete(transactionProduct);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TransactionProduct> getAllByTransaction(List<Transaction> transactions) {

        List<TransactionProduct> transactionProducts = new ArrayList<>();

        transactions.stream().forEach(
                transaction -> {
                    Optional<List<TransactionProduct>> findTransactionProduct = transactionProductRepository.findAllByTransactionId(transaction.getId());
                    if(!findTransactionProduct.isEmpty()) {
                        findTransactionProduct.get().stream().forEach(
                                transactionProduct -> {
                                    transactionProducts.add(transactionProduct);
                                }
                        );
                    }
                }
        );

        return transactionProducts;
    }

    @Transactional(readOnly = true)
    @Override
    public List<TransactionProduct> getAllByWarehouse(Warehouse warehouse) {
        return transactionProductRepository.findAllByWarehouseId(warehouse.getId()).get();
    }

    private TransactionProductResponse parseToTransactionProductResponse(TransactionProduct transactionProduct){

        VendorProduct vendorProduct = vendorProductService.getById(transactionProduct.getVendorProductId());
        Product product = productService.getById(vendorProduct.getProduct().getId());
        Image image = imageService.searchById(product.getProductImage().getId());
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        ImageResponse imageResponse = ImageResponse.builder()
                .name(image.getName())
                .url(baseUrl + APIUrl.PRODUCT_IMAGE_API + image.getName())
                .build();

        TransactionProductsGoodsResponse vendorProductResponse = TransactionProductsGoodsResponse.builder()
                .vendorProductId(transactionProduct.getVendorProductId())
                .vendorName(vendorProduct.getVendor().getVendorName())
                .vendorEmail(vendorProduct.getVendor().getVendorEmail())
                .vendorPhone(vendorProduct.getVendor().getVendorPhone())
                .vendorAddress(vendorProduct.getVendor().getVendorAddress())
                .productName(product.getProductName())
                .productCategory(product.getProductCategory().getCategoryName())
                .productDescription(product.getProductDescription())
                .productImage(imageResponse)
                .build();

        return TransactionProductResponse.builder()
                .userName(transactionProduct.getTransaction().getCustomer().getUserAccount().getUsername())
                .customerEmail(transactionProduct.getTransaction().getCustomer().getEmailCustomer())
                .goodsDetails(vendorProductResponse)
                .createdDate(transactionProduct.getTransaction().getTransactionDate())
                .updatedDate(transactionProduct.getUpdatedAt())
                .stocks(transactionProduct.getStock())
                .price(transactionProduct.getPrice())
                .build();
    }

    private TransactionProduct getByVendorProductFromList(List<TransactionProduct> transactionProducts, String vendorProductId) {
        for (TransactionProduct transactionProduct : transactionProducts) {
            if (transactionProduct.getVendorProductId().equals(vendorProductId)) {
                return transactionProduct;
            }
        }
        return null;
    }

    private WarehouseService getWarehouseService() {
        return applicationContext.getBean(WarehouseService.class);
    }
}
