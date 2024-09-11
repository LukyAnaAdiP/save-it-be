package com.enigma.group5.save_it_backend.controller;

import com.enigma.group5.save_it_backend.constant.APIUrl;
import com.enigma.group5.save_it_backend.constant.ResponseMessage;
import com.enigma.group5.save_it_backend.dto.request.SearchCustomerGoodsRequest;
import com.enigma.group5.save_it_backend.dto.request.SearchProductRequest;
import com.enigma.group5.save_it_backend.dto.response.CommonResponse;
import com.enigma.group5.save_it_backend.dto.response.CustomerGoodsResponse;
import com.enigma.group5.save_it_backend.dto.response.PagingResponse;
import com.enigma.group5.save_it_backend.entity.CustomerGoods;
import com.enigma.group5.save_it_backend.entity.Product;
import com.enigma.group5.save_it_backend.service.CustomerGoodsService;
import com.enigma.group5.save_it_backend.service.GoodsService;
import com.enigma.group5.save_it_backend.utils.CustomerGoodsRequestListUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.CUSTOMER_GOODS)
public class CustomerGoodsController {

    private final CustomerGoodsService customerGoodsService;
    private final GoodsService goodsService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<CommonResponse<?>> createCustomerGoods(@ModelAttribute CustomerGoodsRequestListUtil customerGoodsRequestListUtil) {

        CustomerGoodsResponse customerGoodsResponse = customerGoodsService.create(customerGoodsRequestListUtil.getCustomerGoodsRequests());

        CommonResponse<CustomerGoodsResponse> response = CommonResponse.<CustomerGoodsResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ResponseMessage.SUCCESS_SAVE_DATA)
                .data(customerGoodsResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<CustomerGoodsResponse>>> getAllCustomerGoods(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "price", required = false) String price,
            @RequestParam(name = "stocks", required = false) String stocks,
            @RequestParam(name = "createdAt", required = false) String createdAt

    ) {
        SearchCustomerGoodsRequest request = SearchCustomerGoodsRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .price(price)
                .stocks(stocks)
                .createdAt(createdAt)
                .build();

        Page<CustomerGoodsResponse> customerGoods = customerGoodsService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(customerGoods.getTotalPages())
                .totalElements(customerGoods.getTotalElements())
                .page(customerGoods.getPageable().getPageNumber())
                .size(customerGoods.getPageable().getPageSize())
                .hasNext(customerGoods.hasNext())
                .hasPrevious(customerGoods.hasPrevious())
                .build();

        CommonResponse<List<CustomerGoodsResponse>> response = CommonResponse.<List<CustomerGoodsResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(customerGoods.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/customer")
    public ResponseEntity<CommonResponse<List<CustomerGoodsResponse>>> getAllCustomerGoodsByCustomer(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "price", required = false) String price,
            @RequestParam(name = "stocks", required = false) String stocks,
            @RequestParam(name = "createdAt", required = false) String createdAt

    ) {
        SearchCustomerGoodsRequest request = SearchCustomerGoodsRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .username(SecurityContextHolder.getContext().getAuthentication().getName())
                .price(price)
                .stocks(stocks)
                .createdAt(createdAt)
                .build();

        Page<CustomerGoodsResponse> customerGoods = customerGoodsService.getAllByCustomer(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(customerGoods.getTotalPages())
                .totalElements(customerGoods.getTotalElements())
                .page(customerGoods.getPageable().getPageNumber())
                .size(customerGoods.getPageable().getPageSize())
                .hasNext(customerGoods.hasNext())
                .hasPrevious(customerGoods.hasPrevious())
                .build();

        CommonResponse<List<CustomerGoodsResponse>> response = CommonResponse.<List<CustomerGoodsResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(customerGoods.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}
