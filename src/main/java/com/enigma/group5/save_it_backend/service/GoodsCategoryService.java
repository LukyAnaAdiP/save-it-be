package com.enigma.group5.save_it_backend.service;

import com.enigma.group5.save_it_backend.dto.request.CategoryRequest;
import com.enigma.group5.save_it_backend.dto.request.SearchCategoryRequest;
import com.enigma.group5.save_it_backend.dto.response.CategoryResponse;
import com.enigma.group5.save_it_backend.entity.GoodsCategory;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GoodsCategoryService {
    CategoryResponse create (CategoryRequest categoryRequest);
    Page<GoodsCategory> getAll (SearchCategoryRequest searchCategoryRequest);
    List<GoodsCategory> createBulk (List<CategoryRequest> categoryRequests);
    GoodsCategory getById (String id);
    GoodsCategory getOrSave (String name);
    Long count ();
}
