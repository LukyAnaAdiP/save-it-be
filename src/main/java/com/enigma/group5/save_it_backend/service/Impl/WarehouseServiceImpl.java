package com.enigma.group5.save_it_backend.service.Impl;

import com.enigma.group5.save_it_backend.constant.ResponseMessage;
import com.enigma.group5.save_it_backend.dto.request.*;
import com.enigma.group5.save_it_backend.dto.response.*;
import com.enigma.group5.save_it_backend.entity.*;
import com.enigma.group5.save_it_backend.repository.WarehouseRepository;
import com.enigma.group5.save_it_backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final CustomerGoodsService customerGoodsService;
    private final TransactionProductService transactionProductService;
    private final VendorProductService vendorProductService;
    private final CustomerService customerService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Warehouse create(Warehouse warehouse) {
       return warehouseRepository.saveAndFlush(warehouse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<WarehouseResponse> getAllData (SearchWarehouseRequest searchWarehouseRequest) {

        if (searchWarehouseRequest.getPage() <= 0){
            searchWarehouseRequest.setPage(1);
        }

        String validSortBy = "createdAt";

        Sort sort = Sort.by(Sort.Direction.fromString(searchWarehouseRequest.getDirection()), validSortBy);

        Pageable pageable = PageRequest.of(searchWarehouseRequest.getPage() - 1, searchWarehouseRequest.getSize(), sort);

        List<WarehouseResponse> warehouseResponses = warehouseRepository.findAll().stream().map(
                warehouse -> parseToWarehouseResponse(warehouse)
        ).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), warehouseResponses.size());

        List<WarehouseResponse> pageContent = warehouseResponses.subList(start, end);

        return new PageImpl<>(pageContent, pageable, warehouseResponses.size());
    }

    @Override
    public Page<WarehouseResponse> getAllDataBasedOnCustomer(SearchWarehouseRequest searchWarehouseRequest) {

        if (searchWarehouseRequest.getPage() <= 0){
            searchWarehouseRequest.setPage(1);
        }

        String validSortBy = "createdAt";

        Sort sort = Sort.by(Sort.Direction.fromString(searchWarehouseRequest.getDirection()), validSortBy);

        Pageable pageable = PageRequest.of(searchWarehouseRequest.getPage() - 1, searchWarehouseRequest.getSize(), sort);

        Customer customer = customerService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        List<WarehouseResponse> warehouseResponses = warehouseRepository.findAll().stream().map(
                warehouse -> parseToWarehouseResponseBasedOnCustomer(warehouse, customer)
        ).filter(warehouseResponse -> warehouseResponse != null).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), warehouseResponses.size());

        List<WarehouseResponse> pageContent = warehouseResponses.subList(start, end);

        return new PageImpl<>(pageContent, pageable, warehouseResponses.size());
    }

    @Override
    public WarehouseUpdateResponse update(WarehouseUpdateRequest warehouseUpdateRequest) {

        WarehouseUpdateResponse<?> updateResponse = new WarehouseUpdateResponse();

        if (warehouseUpdateRequest.getType().equals("TRANSACTION PRODUCT")) {

            TransactionProductUpdateRequest transactionProductUpdateRequest = TransactionProductUpdateRequest.builder()
                    .transactionProductId(warehouseUpdateRequest.getWarehouseId())
                    .price(warehouseUpdateRequest.getPrice())
                    .build();

            TransactionProductResponse updatedTransactionProduct = transactionProductService.update(transactionProductUpdateRequest);

            Map<String, Object> goods = Map.of(
                    "vendorProductId", updatedTransactionProduct.getGoodsDetails().getVendorProductId(),
                    "vendorName", updatedTransactionProduct.getGoodsDetails().getVendorName(),
                    "vendorEmail", updatedTransactionProduct.getGoodsDetails().getVendorEmail(),
                    "vendorPhoneNumber", updatedTransactionProduct.getGoodsDetails().getVendorPhone(),
                    "vendorAddress", updatedTransactionProduct.getGoodsDetails().getVendorAddress(),
                    "goodsName", updatedTransactionProduct.getGoodsDetails().getProductName(),
                    "goodsCategory", updatedTransactionProduct.getGoodsDetails().getProductCategory(),
                    "goodsDescription", updatedTransactionProduct.getGoodsDetails().getProductDescription(),
                    "goodsImage", ImageResponse.builder()
                                    .name(updatedTransactionProduct.getGoodsDetails().getProductImage().getName())
                                    .url(updatedTransactionProduct.getGoodsDetails().getProductImage().getUrl())
                            .build()
            );

            Warehouse warehouse = transactionProductService.getById(warehouseUpdateRequest.getWarehouseId()).getWarehouse();
            warehouse.setUpdatedAt(new Date());
            warehouseRepository.saveAndFlush(warehouse);

            updateResponse  = WarehouseUpdateResponse.<Map<String, Object>>builder()
                    .username(updatedTransactionProduct.getUserName())
                    .customerEmail(updatedTransactionProduct.getCustomerEmail())
                    .type("TRANSACTION PRODUCT")
                    .goods(goods)
                    .createdAt(warehouse.getCreatedAt())
                    .updatedAt(warehouse.getUpdatedAt())
                    .price(updatedTransactionProduct.getPrice())
                    .stocks(updatedTransactionProduct.getStocks())
                    .build();


        } else if (warehouseUpdateRequest.getType().equals("CUSTOMER GOODS")){

            CustomerGoodsUpdateRequest customerGoodsUpdateRequest = CustomerGoodsUpdateRequest.builder()
                    .customerGoodsId(warehouseUpdateRequest.getWarehouseId())
                    .goodsName(warehouseUpdateRequest.getGoodsName())
                    .goodsCategoryId(warehouseUpdateRequest.getGoodsCategoryId())
                    .goodsDescription(warehouseUpdateRequest.getGoodsDescription())
                    .price(warehouseUpdateRequest.getPrice())
                    .stocks(warehouseUpdateRequest.getStocks())
                    .build();

            CustomerGoodsResponse updatedCustomerGoods = customerGoodsService.update(customerGoodsUpdateRequest);

            Warehouse warehouse = customerGoodsService.getById(warehouseUpdateRequest.getWarehouseId()).getWarehouse();
            warehouse.setUpdatedAt(new Date());
            warehouseRepository.saveAndFlush(warehouse);

            updateResponse = WarehouseUpdateResponse.builder()
                    .username(updatedCustomerGoods.getUsername())
                    .customerEmail(updatedCustomerGoods.getCustomerEmail())
                    .type("CUSTOMER GOODS")
                    .goods(updatedCustomerGoods.getGoods())
                    .createdAt(warehouse.getCreatedAt())
                    .updatedAt(warehouse.getUpdatedAt())
                    .price(updatedCustomerGoods.getPrice())
                    .stocks(updatedCustomerGoods.getStocks())
                    .build();

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Type not found");
        }

        return updateResponse;
    }

    @Override
    public void delete(WarehouseDeleteRequest warehouseDeleteRequest) {

        if(warehouseDeleteRequest.getType().equals("TRANSACTION PRODUCT")) {
            transactionProductService.deleteById(warehouseDeleteRequest.getWarehouseId());
        } else if (warehouseDeleteRequest.getType().equals("CUSTOMER GOODS")) {
            customerGoodsService.deleteById(warehouseDeleteRequest.getWarehouseId());
        }

    }

    private WarehouseResponse parseToWarehouseResponse(Warehouse warehouse) {

        List<CustomerGoods> customerGoods = customerGoodsService.getAllByWarehouse(warehouse);
        List<TransactionProduct> transactionProducts = transactionProductService.getAllByWarehouse(warehouse);

        if(!customerGoods.isEmpty()) {

            List<WarehouseGoodsResponse> warehouseGoodsResponses = customerGoods.stream().map(
                goods -> WarehouseGoodsResponse.builder()
                        .warehouseId(goods.getId())
                        .goodsName(goods.getGoods().getGoodsName())
                        .goodsCategoryId(goods.getGoods().getGoodsCategory().getId())
                        .goodsCategoryName(goods.getGoods().getGoodsCategory().getCategoryName())
                        .goodsDescription(goods.getGoods().getGoodsDescription())
                        .goodsImage(
                                ImageResponse.builder()
                                        .name(goods.getGoods().getGoodsImage().getName())
                                        .url(goods.getGoods().getGoodsImage().getPath())
                                        .build()
                        )
                        .price(goods.getPrice())
                        .stocks(goods.getStocks())
                        .vendorProductId(null)
                        .vendorDetails(null)
                        .build()
            ).toList();

             WarehouseResponse warehouseResponses = WarehouseResponse.builder()
                    .type("CUSTOMER GOODS")
                    .username(customerGoods.get(0).getUserAccount().getUsername())
                    .customerEmail(customerGoods.get(0).getCustomer().getEmailCustomer())
                    .goods(warehouseGoodsResponses)
                    .createdAt(customerGoods.get(0).getCreatedAt())
                    .updatedAt(customerGoods.get(0).getUpdatedAt())
                    .build();
             return warehouseResponses;

        } else if (!transactionProducts.isEmpty()) {

            List<WarehouseGoodsResponse> warehouseGoodsResponses = transactionProducts.stream().map(
                    goods -> {

                        VendorProduct vendorProduct = vendorProductService.getById(goods.getVendorProductId());

                        return WarehouseGoodsResponse.builder()
                                .warehouseId(goods.getId())
                                .goodsName(vendorProduct.getProduct().getProductName())
                                .goodsCategoryId(vendorProduct.getProduct().getProductCategory().getId())
                                .goodsCategoryName(vendorProduct.getProduct().getProductCategory().getCategoryName())
                                .goodsDescription(vendorProduct.getProduct().getProductDescription())
                                .goodsImage(
                                        ImageResponse.builder()
                                                .name(vendorProduct.getProduct().getProductImage().getName())
                                                .url(vendorProduct.getProduct().getProductImage().getPath())
                                                .build()
                                )
                                .price(goods.getPrice())
                                .stocks(goods.getStock())
                                .vendorProductId(goods.getVendorProductId())
                                .vendorDetails(
                                        VendorResponse.builder()
                                                .vendorId(vendorProduct.getVendor().getId())
                                                .vendorName(vendorProduct.getVendor().getVendorName())
                                                .vendorEmail(vendorProduct.getVendor().getVendorEmail())
                                                .vendorPhone(vendorProduct.getVendor().getVendorPhone())
                                                .vendorAddress(vendorProduct.getVendor().getVendorAddress())
                                                .build()
                                )
                                .build();
                    }
            ).toList();

            WarehouseResponse warehouseResponses = WarehouseResponse.builder()
                    .type("TRANSACTION PRODUCT")
                    .username(transactionProducts.get(0).getTransaction().getCustomer().getUserAccount().getUsername())
                    .customerEmail(transactionProducts.get(0).getTransaction().getCustomer().getEmailCustomer())
                    .goods(warehouseGoodsResponses)
                    .createdAt(warehouse.getCreatedAt())
                    .updatedAt(warehouse.getUpdatedAt())
                    .build();
            return warehouseResponses;

        } else {
            throw new RuntimeException("Warehouse is empty");
        }
    }

    private WarehouseResponse parseToWarehouseResponseBasedOnCustomer(Warehouse warehouse, Customer customer) {

        List<CustomerGoods> customerGoods = customerGoodsService.getAllByWarehouse(warehouse).stream().filter(
                goods -> goods.getCustomer().getId() == customer.getId()
        ).toList();

        List<TransactionProduct> transactionProducts = transactionProductService.getAllByWarehouse(warehouse).stream().filter(
                transactionProduct -> transactionProduct.getTransaction().getCustomer().getId() == customer.getId()
        ).toList();


        if(!customerGoods.isEmpty()) {

            List<WarehouseGoodsResponse> warehouseGoodsResponses = customerGoods.stream().map(
                    goods -> WarehouseGoodsResponse.builder()
                            .warehouseId(goods.getId())
                            .goodsName(goods.getGoods().getGoodsName())
                            .goodsCategoryId(goods.getGoods().getGoodsCategory().getId())
                            .goodsCategoryName(goods.getGoods().getGoodsCategory().getCategoryName())
                            .goodsDescription(goods.getGoods().getGoodsDescription())
                            .goodsImage(
                                    ImageResponse.builder()
                                            .name(goods.getGoods().getGoodsImage().getName())
                                            .url(goods.getGoods().getGoodsImage().getPath())
                                            .build()
                            )
                            .price(goods.getPrice())
                            .stocks(goods.getStocks())
                            .vendorProductId(null)
                            .vendorDetails(null)
                            .build()
            ).toList();

            WarehouseResponse warehouseResponses = WarehouseResponse.builder()
                    .type("CUSTOMER GOODS")
                    .username(customerGoods.get(0).getUserAccount().getUsername())
                    .customerEmail(customerGoods.get(0).getCustomer().getEmailCustomer())
                    .goods(warehouseGoodsResponses)
                    .createdAt(customerGoods.get(0).getCreatedAt())
                    .updatedAt(customerGoods.get(0).getUpdatedAt())
                    .build();

            return warehouseResponses;

        } else if (!transactionProducts.isEmpty()) {

            List<WarehouseGoodsResponse> warehouseGoodsResponses = transactionProducts.stream()
                    .filter(goods -> goods.getVendorProductId() != null)
                    .map(
                    goods -> {

                        VendorProduct vendorProduct = vendorProductService.getById(goods.getVendorProductId());

                        return WarehouseGoodsResponse.builder()
                                .warehouseId(goods.getId())
                                .goodsName(vendorProduct.getProduct().getProductName())
                                .goodsCategoryId(vendorProduct.getProduct().getProductCategory().getId())
                                .goodsCategoryName(vendorProduct.getProduct().getProductCategory().getCategoryName())
                                .goodsDescription(vendorProduct.getProduct().getProductDescription())
                                .goodsImage(
                                        ImageResponse.builder()
                                                .name(vendorProduct.getProduct().getProductImage().getName())
                                                .url(vendorProduct.getProduct().getProductImage().getPath())
                                                .build()
                                )
                                .price(goods.getPrice())
                                .stocks(goods.getStock())
                                .vendorProductId(goods.getVendorProductId())
                                .vendorDetails(
                                        VendorResponse.builder()
                                                .vendorId(vendorProduct.getVendor().getId())
                                                .vendorName(vendorProduct.getVendor().getVendorName())
                                                .vendorEmail(vendorProduct.getVendor().getVendorEmail())
                                                .vendorPhone(vendorProduct.getVendor().getVendorPhone())
                                                .vendorAddress(vendorProduct.getVendor().getVendorAddress())
                                                .build()
                                )
                                .build();
                    }
            ).toList();

            WarehouseResponse warehouseResponses = WarehouseResponse.builder()
                    .type("TRANSACTION PRODUCT")
                    .username(transactionProducts.get(0).getTransaction().getCustomer().getUserAccount().getUsername())
                    .customerEmail(transactionProducts.get(0).getTransaction().getCustomer().getEmailCustomer())
                    .goods(warehouseGoodsResponses)
                    .createdAt(warehouse.getCreatedAt())
                    .updatedAt(warehouse.getUpdatedAt())
                    .build();

            return warehouseResponses;

        } else {
            return null;
        }
    }
}
