package com.enigma.group5.save_it_backend.controller;

import com.enigma.group5.save_it_backend.constant.APIUrl;
import com.enigma.group5.save_it_backend.constant.ResponseMessage;
import com.enigma.group5.save_it_backend.dto.request.CategoryRequest;
import com.enigma.group5.save_it_backend.dto.request.SearchCategoryRequest;
import com.enigma.group5.save_it_backend.dto.response.CategoryResponse;
import com.enigma.group5.save_it_backend.dto.response.CommonResponse;
import com.enigma.group5.save_it_backend.dto.response.PagingResponse;
import com.enigma.group5.save_it_backend.entity.GoodsCategory;
import com.enigma.group5.save_it_backend.entity.ProductCategory;
import com.enigma.group5.save_it_backend.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.PRODUCT_CATEGORY)
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<CommonResponse<CategoryResponse>> create(
            @RequestPart(name = "categoryName") String categoryName,
            @RequestPart(name = "image", required = false) MultipartFile image
    ) {

        CategoryRequest categoryRequest = CategoryRequest.builder()
                .productCategoryName(categoryName)
                .image(image)
                .build();

        CategoryResponse categoryResponse = productCategoryService.create(categoryRequest);
        CommonResponse<CategoryResponse> response = CommonResponse.<CategoryResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ResponseMessage.SUCCESS_SAVE_DATA)
                .data(categoryResponse)
                .build();
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<CommonResponse<List<ProductCategory>>> getAllCategory(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "vendorName") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "categoryName", required = false) String categoryName
    ) {

        SearchCategoryRequest request = SearchCategoryRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .categoryName(categoryName)
                .build();

        Page<ProductCategory> allCategory = productCategoryService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(allCategory.getTotalPages())
                .totalElements(allCategory.getTotalElements())
                .page(allCategory.getPageable().getPageNumber())
                .size(allCategory.getPageable().getPageSize())
                .hasNext(allCategory.hasNext())
                .hasPrevious(allCategory.hasPrevious())
                .build();

        CommonResponse<List<ProductCategory>> response = CommonResponse.<List<ProductCategory>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(allCategory.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }

}
