package com.enigma.group5.save_it_backend.service;

import com.enigma.group5.save_it_backend.dto.request.CategoryRequest;
import com.enigma.group5.save_it_backend.dto.request.SearchCategoryRequest;
import com.enigma.group5.save_it_backend.dto.response.CategoryResponse;
import com.enigma.group5.save_it_backend.entity.ProductCategory;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductCategoryService {
    CategoryResponse create(CategoryRequest categoryRequest);
    Page<ProductCategory> getAll(SearchCategoryRequest searchCategoryRequest);
    List<ProductCategory> createBulk(List<CategoryRequest> categoryRequests);
    ProductCategory getById(String id);
    ProductCategory getOrSave(String name);
    Long count();
}
