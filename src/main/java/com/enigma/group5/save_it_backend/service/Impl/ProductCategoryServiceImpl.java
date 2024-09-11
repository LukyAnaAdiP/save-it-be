package com.enigma.group5.save_it_backend.service.Impl;

import com.enigma.group5.save_it_backend.constant.DirectoryImage;
import com.enigma.group5.save_it_backend.dto.request.CategoryRequest;
import com.enigma.group5.save_it_backend.dto.request.SearchCategoryRequest;
import com.enigma.group5.save_it_backend.dto.response.CategoryResponse;
import com.enigma.group5.save_it_backend.dto.response.ImageResponse;
import com.enigma.group5.save_it_backend.entity.Image;
import com.enigma.group5.save_it_backend.entity.ProductCategory;
import com.enigma.group5.save_it_backend.repository.ProductCategoryRepository;
import com.enigma.group5.save_it_backend.service.ImageService;
import com.enigma.group5.save_it_backend.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ImageService imageService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CategoryResponse create(CategoryRequest categoryRequest) {

        Image image = imageService.saveToCloud(categoryRequest.getImage(), DirectoryImage.CATEGORY);

        ProductCategory newCategory = productCategoryRepository.saveAndFlush(
                ProductCategory.builder()
                        .categoryName(categoryRequest.getProductCategoryName())
                        .productCategoryImage(image)
                        .build()
        );

        return CategoryResponse.builder()
                .id(newCategory.getId())
                .categoryName(newCategory.getCategoryName())
                .imageResponse(ImageResponse.builder()
                        .name(image.getName())
                        .url(image.getPath())
                        .build())
                .build();
    }

    @Override
    public Page<ProductCategory> getAll(SearchCategoryRequest searchCategoryRequest) {
        if (searchCategoryRequest.getPage() <= 0){
            searchCategoryRequest.setPage(1);
        }
        String validSortBy = "categoryName";

        Sort sort = Sort.by(Sort.Direction.fromString(searchCategoryRequest.getDirection()), validSortBy);

        Pageable pageable = PageRequest.of((searchCategoryRequest.getPage() -1), searchCategoryRequest.getSize(), sort);

        return productCategoryRepository.findAll(pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ProductCategory> createBulk(List<CategoryRequest> categoryRequests) {

        List<ProductCategory> productCategories = categoryRequests.stream().map(categoryRequest -> ProductCategory.builder()
                .categoryName(categoryRequest.getProductCategoryName())
                .productCategoryImage(imageService.createInit(categoryRequest.getImage()))
                .build()).toList();
        return productCategoryRepository.saveAllAndFlush(productCategories);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductCategory getById(String id) {
        Optional<ProductCategory> productCategory = productCategoryRepository.findById(id);
        return productCategory.orElseThrow(() -> new RuntimeException("Product Category not found"));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductCategory getOrSave(String name) {
        return productCategoryRepository.findByCategoryName(name.toLowerCase())
                .orElseGet(() -> productCategoryRepository.saveAndFlush(
                        ProductCategory.builder()
                                .categoryName(name.toLowerCase())
                                .build()
                ));
    }

    @Transactional(readOnly = true)
    @Override
    public Long count() {
        return productCategoryRepository.count();
    }
}
