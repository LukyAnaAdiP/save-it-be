package com.enigma.group5.save_it_backend.service.Impl;

import com.enigma.group5.save_it_backend.constant.DirectoryImage;
import com.enigma.group5.save_it_backend.dto.request.*;
import com.enigma.group5.save_it_backend.dto.response.*;
import com.enigma.group5.save_it_backend.entity.*;
import com.enigma.group5.save_it_backend.repository.CustomerGoodsRepository;
import com.enigma.group5.save_it_backend.service.*;
import com.enigma.group5.save_it_backend.specification.CustomerGoodsSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerGoodsServiceImpl implements CustomerGoodsService {

    private final CustomerGoodsRepository customerGoodsRepository;
    private final GoodsService goodsService;
    private final CustomerService customerService;
    private final ImageService imageService;
    private final GoodsCategoryService goodsCategoryService;

    private final ApplicationContext applicationContext;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerGoodsResponse create(List<CustomerGoodsRequest> customerGoodsRequest) {
        Warehouse warehouse = getWarehouseService().create(Warehouse.builder()
                .createdAt(new Date())
                .updatedAt(new Date())
                .build());
        List <CustomerGoods> newCustomerGoods = customerGoodsRequest.stream().map(
                request -> {

                    Image goodsImage = imageService.saveToCloud(request.getGoodsImage(), DirectoryImage.GOODS);

                    Customer customer = customerService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

                    Goods goods = Goods.builder()
                            .goodsName(request.getGoodsName())
                            .goodsCategory(goodsCategoryService.getById(request.getGoodsCategoryId()))
                            .goodsDescription(request.getGoodsDescription())
                            .goodsImage(goodsImage)
                            .build();
                    goodsService.create(goods);

                  return CustomerGoods.builder()
                          .userAccount(customer.getUserAccount())
                          .customer(customer)
                          .warehouse(warehouse)
                          .goods(goods)
                          .price(request.getPrice())
                          .stocks(request.getStocks())
                          .createdAt(new Date())
                          .updatedAt(new Date())
                          .build();
                }
        ).toList();
        customerGoodsRepository.saveAllAndFlush(newCustomerGoods);

        //create goods response
        List<GoodsResponse> goodsResponses = newCustomerGoods.stream().map(
                goods -> {

                    ImageResponse imageResponse = ImageResponse.builder()
                            .name(goods.getGoods().getGoodsImage().getName())
                            .url(goods.getGoods().getGoodsImage().getPath())
                            .build();

                    return GoodsResponse.builder()
                            .goodsName(goods.getGoods().getGoodsName())
                            .goodsCategory(goods.getGoods().getGoodsCategory().getCategoryName())
                            .goodsDescription(goods.getGoods().getGoodsDescription())
                            .imageResponse(imageResponse)
                            .build();
                }
        ).toList();

        CustomerGoodsResponse<List<GoodsResponse>> customerGoodsResponse = CustomerGoodsResponse.<List<GoodsResponse>>builder()
                .username(newCustomerGoods.get(0).getUserAccount().getUsername())
                .customerEmail(newCustomerGoods.get(0).getCustomer().getEmailCustomer())
                .goods(goodsResponses)
                .stocks(newCustomerGoods.get(0).getStocks())
                .price(newCustomerGoods.get(0).getPrice())
                .createdDate(newCustomerGoods.get(0).getCreatedAt())
                .updatedDate(newCustomerGoods.get(0).getUpdatedAt())
                .build();

        return customerGoodsResponse;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CustomerGoodsResponse> getAll(SearchCustomerGoodsRequest searchCustomerGoodsRequest) {

        if (searchCustomerGoodsRequest.getPage() <= 0){
            searchCustomerGoodsRequest.setPage(1);
        }

        String validSortBy;
        if ("createdAt".equalsIgnoreCase(searchCustomerGoodsRequest.getSortBy()) || "price".equalsIgnoreCase(searchCustomerGoodsRequest.getSortBy())
                || "stocks".equals(searchCustomerGoodsRequest.getSortBy())) {
            validSortBy = searchCustomerGoodsRequest.getSortBy();
        }
        else {
            validSortBy = "createdAt";
        }

        Sort sort = Sort.by(Sort.Direction.fromString(searchCustomerGoodsRequest.getDirection()), validSortBy);

        Pageable pageable = PageRequest.of(searchCustomerGoodsRequest.getPage() - 1, searchCustomerGoodsRequest.getSize(), sort);

        Specification<CustomerGoods> specification = CustomerGoodsSpecification.getSpecification(searchCustomerGoodsRequest);

        List<CustomerGoods> customerGoods = customerGoodsRepository.findAll(specification);

        List<CustomerGoodsResponse> customerGoodsResponses = customerGoods.stream().map(
                data -> parseToCustomerGoodsResponse(data)
        ).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), customerGoodsResponses.size());

        List<CustomerGoodsResponse> pageContent = customerGoodsResponses.subList(start, end);

        return new PageImpl<>(pageContent, pageable, customerGoodsResponses.size());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CustomerGoodsResponse> getAllByCustomer(SearchCustomerGoodsRequest searchCustomerGoodsRequest) {

        if (searchCustomerGoodsRequest.getPage() <= 0){
            searchCustomerGoodsRequest.setPage(1);
        }

        String validSortBy;
        if ("createdAt".equalsIgnoreCase(searchCustomerGoodsRequest.getSortBy()) || "price".equalsIgnoreCase(searchCustomerGoodsRequest.getSortBy())
                || "stock".equals(searchCustomerGoodsRequest.getSortBy())) {
            validSortBy = searchCustomerGoodsRequest.getSortBy();
        }
        else {
            validSortBy = "createdAt";
        }

        Sort sort = Sort.by(Sort.Direction.fromString(searchCustomerGoodsRequest.getDirection()), validSortBy);

        Pageable pageable = PageRequest.of(searchCustomerGoodsRequest.getPage() - 1, searchCustomerGoodsRequest.getSize(), sort);

        Customer customer = customerService.getByUsername(searchCustomerGoodsRequest.getUsername());

        List<CustomerGoods> customerGoods = customerGoodsRepository.findAllByCustomerId(customer.getId()).orElse(null);

        List<CustomerGoodsResponse> customerGoodsResponses = customerGoods.stream().map(
                data -> parseToCustomerGoodsResponse(data)
        ).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), customerGoodsResponses.size());

        List<CustomerGoodsResponse> pageContent = customerGoodsResponses.subList(start, end);

        return new PageImpl<>(pageContent, pageable, customerGoodsResponses.size());
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerGoods getById(String id) {
        Optional<CustomerGoods> customerGoods = customerGoodsRepository.findById(id);
        return customerGoods.orElseThrow(() -> new RuntimeException("Item not found customer goods warehouse"));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerGoodsResponse update(CustomerGoodsUpdateRequest customerGoodsUpdateRequest) {

        CustomerGoods currentCustomerGoods = getById(customerGoodsUpdateRequest.getCustomerGoodsId());

        if (!currentCustomerGoods.getUserAccount().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            throw new RuntimeException("You are not allowed to update this transaction product");
        }

        if (customerGoodsUpdateRequest.getStocks() != null) {
            currentCustomerGoods.setStocks(customerGoodsUpdateRequest.getStocks());
        }
        if (customerGoodsUpdateRequest.getPrice() != null) {
            currentCustomerGoods.setPrice(customerGoodsUpdateRequest.getPrice());
        }
        currentCustomerGoods.setUpdatedAt(new Date());

        customerGoodsRepository.saveAndFlush(currentCustomerGoods);

        GoodsUpdateRequest updateRequest = GoodsUpdateRequest.builder()
                .goodsId(currentCustomerGoods.getGoods().getId())
                .goodsName(customerGoodsUpdateRequest.getGoodsName())
                .goodsCategoryId(customerGoodsUpdateRequest.getGoodsCategoryId())
                .goodsDescription(customerGoodsUpdateRequest.getGoodsDescription())
                .build();

        goodsService.update(updateRequest);

        return parseToCustomerGoodsResponse(currentCustomerGoods);
    }

    @Override
    public Long countByCustomer(Customer customer) {
        Long count = customerGoodsRepository.findAllByCustomerId(customer.getId()).get().stream()
                .reduce(0L, (subtotal, element) -> subtotal + element.getStocks(), Long::sum);
        return count;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerGoods> getAllByWarehouse(Warehouse warehouse) {
        return customerGoodsRepository.findAllByWarehouseId(warehouse.getId()).get();
    }

    @Override
    public void deleteById(String id) {
        CustomerGoods customerGoods = getById(id);
        if(!customerGoods.getUserAccount().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            throw new RuntimeException("You are not allowed to delete this transaction product");
        }
        String goodsId = customerGoods.getGoods().getId();
        customerGoodsRepository.delete(customerGoods);
        goodsService.delete(goodsId);
    }

    private WarehouseService getWarehouseService() {
        return applicationContext.getBean(WarehouseService.class);
    }

    private CustomerGoodsResponse parseToCustomerGoodsResponse (CustomerGoods customerGoods){

        GoodsResponse goodsResponse = GoodsResponse.builder()
                .goodsName(customerGoods.getGoods().getGoodsName())
                .goodsCategory(customerGoods.getGoods().getGoodsCategory().getCategoryName())
                .goodsDescription(customerGoods.getGoods().getGoodsDescription())
                .imageResponse(ImageResponse.builder()
                        .name(customerGoods.getGoods().getGoodsImage().getName())
                        .url(customerGoods.getGoods().getGoodsImage().getPath())
                        .build())
                .build();

        CustomerGoodsResponse<GoodsResponse> customerGoodsResponse = CustomerGoodsResponse.<GoodsResponse>builder()
                .username(customerGoods.getUserAccount().getUsername())
                .customerEmail(customerGoods.getCustomer().getEmailCustomer())
                .goods(goodsResponse)
                .stocks(customerGoods.getStocks())
                .price(customerGoods.getPrice())
                .createdDate(customerGoods.getCreatedAt())
                .updatedDate(customerGoods.getUpdatedAt())
                .build();

        return customerGoodsResponse;
    }
}