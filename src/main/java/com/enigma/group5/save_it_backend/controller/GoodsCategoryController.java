package com.enigma.group5.save_it_backend.controller;

import com.enigma.group5.save_it_backend.constant.APIUrl;
import com.enigma.group5.save_it_backend.constant.ResponseMessage;
import com.enigma.group5.save_it_backend.dto.request.CategoryRequest;
import com.enigma.group5.save_it_backend.dto.request.SearchCategoryRequest;
import com.enigma.group5.save_it_backend.dto.response.CategoryResponse;
import com.enigma.group5.save_it_backend.dto.response.CommonResponse;
import com.enigma.group5.save_it_backend.dto.response.PagingResponse;
import com.enigma.group5.save_it_backend.entity.GoodsCategory;
import com.enigma.group5.save_it_backend.entity.Vendor;
import com.enigma.group5.save_it_backend.service.GoodsCategoryService;
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
@RequestMapping(path = APIUrl.GOODS_CATEGORY)
public class GoodsCategoryController {

    private final GoodsCategoryService goodsCategoryService;

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

        CategoryResponse categoryResponse = goodsCategoryService.create(categoryRequest);
        CommonResponse<CategoryResponse> response = CommonResponse.<CategoryResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ResponseMessage.SUCCESS_SAVE_DATA)
                .data(categoryResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<GoodsCategory>>> getAllCategory(
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

        Page<GoodsCategory> allCategory = goodsCategoryService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(allCategory.getTotalPages())
                .totalElements(allCategory.getTotalElements())
                .page(allCategory.getPageable().getPageNumber())
                .size(allCategory.getPageable().getPageSize())
                .hasNext(allCategory.hasNext())
                .hasPrevious(allCategory.hasPrevious())
                .build();

        CommonResponse<List<GoodsCategory>> response = CommonResponse.<List<GoodsCategory>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(allCategory.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }

}
