package com.enigma.group5.save_it_backend.controller;

import com.enigma.group5.save_it_backend.constant.APIUrl;
import com.enigma.group5.save_it_backend.constant.ResponseMessage;
import com.enigma.group5.save_it_backend.dto.request.SearchVendorProductRequest;
import com.enigma.group5.save_it_backend.dto.response.CommonResponse;
import com.enigma.group5.save_it_backend.dto.response.GetAllProductByVendorResponse;
import com.enigma.group5.save_it_backend.dto.response.PagingResponse;
import com.enigma.group5.save_it_backend.dto.response.VendorProductResponse;
import com.enigma.group5.save_it_backend.service.VendorProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.VENDOR_PRODUCT_API)
public class VendorProductController {

    private final VendorProductService vendorProductService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<VendorProductResponse>>> getAllVendor(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        SearchVendorProductRequest request = SearchVendorProductRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();

        Page<VendorProductResponse> vendorAll = vendorProductService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(vendorAll.getTotalPages())
                .totalElements(vendorAll.getTotalElements())
                .page(vendorAll.getPageable().getPageNumber())
                .size(vendorAll.getPageable().getPageSize())
                .hasNext(vendorAll.hasNext())
                .hasPrevious(vendorAll.hasPrevious())
                .build();

        CommonResponse<List<VendorProductResponse>> response = CommonResponse.<List<VendorProductResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(vendorAll.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    @GetMapping(path = "/product")
    public ResponseEntity<CommonResponse<List<GetAllProductByVendorResponse>>> getAllProductByVendor(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        SearchVendorProductRequest request = SearchVendorProductRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();

        Page<GetAllProductByVendorResponse> allProductByVendor = vendorProductService.getAllProductByVendor(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(allProductByVendor.getTotalPages())
                .totalElements(allProductByVendor.getTotalElements())
                .page(allProductByVendor.getPageable().getPageNumber())
                .size(allProductByVendor.getPageable().getPageSize())
                .hasNext(allProductByVendor.hasNext())
                .hasPrevious(allProductByVendor.hasPrevious())
                .build();

        CommonResponse<List<GetAllProductByVendorResponse>> response = CommonResponse.<List<GetAllProductByVendorResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(allProductByVendor.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}
