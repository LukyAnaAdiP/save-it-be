package com.enigma.group5.save_it_backend.controller;

import com.enigma.group5.save_it_backend.constant.APIUrl;
import com.enigma.group5.save_it_backend.constant.ResponseMessage;
import com.enigma.group5.save_it_backend.dto.request.SearchTransactionProductRequest;
import com.enigma.group5.save_it_backend.dto.response.CommonResponse;
import com.enigma.group5.save_it_backend.dto.response.PagingResponse;
import com.enigma.group5.save_it_backend.dto.response.TransactionProductResponse;
import com.enigma.group5.save_it_backend.entity.TransactionProduct;
import com.enigma.group5.save_it_backend.service.TransactionProductService;
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
@RequestMapping(path = APIUrl.TRANSACTION_PRODUCT)
public class TransactionProductController {
    private final TransactionProductService transactionProductService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<TransactionProductResponse>>> getAllTransactionProduct(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "vpId") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "price", required = false) String price,
            @RequestParam(name = "stocks", required = false) String stocks,
            @RequestParam(name = "createdAt", required = false) String createdAt
    ){
        SearchTransactionProductRequest request = SearchTransactionProductRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .stocks(stocks)
                .price(price)
                .createdAt(createdAt)
                .build();

        Page<TransactionProductResponse> transactionProductResponses = transactionProductService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(transactionProductResponses.getTotalPages())
                .totalElements(transactionProductResponses.getTotalElements())
                .page(transactionProductResponses.getPageable().getPageNumber())
                .size(transactionProductResponses.getPageable().getPageSize())
                .hasNext(transactionProductResponses.hasNext())
                .hasPrevious(transactionProductResponses.hasPrevious())
                .build();

        CommonResponse<List<TransactionProductResponse>> response = CommonResponse.<List<TransactionProductResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(transactionProductResponses.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/customer")
    public ResponseEntity<CommonResponse<List<TransactionProductResponse>>> getAllTransactionProductBaseOnCustomer(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "vpId") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "price", required = false) String price,
            @RequestParam(name = "stocks", required = false) String stocks,
            @RequestParam(name = "createdAt", required = false) String createdAt
    ){
        SearchTransactionProductRequest request = SearchTransactionProductRequest.builder()
                .username(SecurityContextHolder.getContext().getAuthentication().getName())
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .stocks(stocks)
                .price(price)
                .createdAt(createdAt)
                .build();

        Page<TransactionProductResponse> transactionProductResponses = transactionProductService.getAllByCustomer(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(transactionProductResponses.getTotalPages())
                .totalElements(transactionProductResponses.getTotalElements())
                .page(transactionProductResponses.getPageable().getPageNumber())
                .size(transactionProductResponses.getPageable().getPageSize())
                .hasNext(transactionProductResponses.hasNext())
                .hasPrevious(transactionProductResponses.hasPrevious())
                .build();

        CommonResponse<List<TransactionProductResponse>> response = CommonResponse.<List<TransactionProductResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(transactionProductResponses.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = APIUrl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<TransactionProduct>> getWarehouseById(@PathVariable String id) {
        TransactionProduct byId = transactionProductService.getById(id);

        CommonResponse<TransactionProduct> response = CommonResponse.<TransactionProduct>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(byId)
                .build();

        return ResponseEntity.ok(response);
    }


    @DeleteMapping(path = APIUrl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<String>> deleteById(@PathVariable String id) {
        transactionProductService.deleteById(id);

        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_DELETE_DATA)
                .build();
        return ResponseEntity.ok(response) ;
    }

}
