package com.enigma.group5.save_it_backend.service.Impl;

import com.enigma.group5.save_it_backend.constant.DirectoryImage;
import com.enigma.group5.save_it_backend.dto.request.CategoryRequest;
import com.enigma.group5.save_it_backend.dto.request.SearchCategoryRequest;
import com.enigma.group5.save_it_backend.dto.response.CategoryResponse;
import com.enigma.group5.save_it_backend.dto.response.ImageResponse;
import com.enigma.group5.save_it_backend.entity.GoodsCategory;
import com.enigma.group5.save_it_backend.entity.Image;
import com.enigma.group5.save_it_backend.entity.ProductCategory;
import com.enigma.group5.save_it_backend.repository.GoodsCategoryRepository;
import com.enigma.group5.save_it_backend.service.GoodsCategoryService;
import com.enigma.group5.save_it_backend.service.ImageService;
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
public class GoodsCategoryServiceImpl implements GoodsCategoryService {

    private final GoodsCategoryRepository goodsCategoryRepository;
    private final ImageService imageService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CategoryResponse create(CategoryRequest categoryRequest) {

        Image image = imageService.saveToCloud(categoryRequest.getImage(), DirectoryImage.CATEGORY);

        GoodsCategory newCategory = goodsCategoryRepository.saveAndFlush(
                GoodsCategory.builder()
                        .categoryName(categoryRequest.getProductCategoryName())
                        .goodsCategoryImage(image)
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
    public Page<GoodsCategory> getAll(SearchCategoryRequest searchCategoryRequest) {
        if (searchCategoryRequest.getPage() <= 0){
            searchCategoryRequest.setPage(1);
        }
        String validSortBy = "categoryName";

        Sort sort = Sort.by(Sort.Direction.fromString(searchCategoryRequest.getDirection()), validSortBy);

        Pageable pageable = PageRequest.of((searchCategoryRequest.getPage() -1), searchCategoryRequest.getSize(), sort);

        return goodsCategoryRepository.findAll(pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<GoodsCategory> createBulk(List<CategoryRequest> categoryRequests) {
        List<GoodsCategory> goodsCategories = categoryRequests.stream().map(categoryRequest -> GoodsCategory.builder()
                .categoryName(categoryRequest.getProductCategoryName())
                .goodsCategoryImage(imageService.createInit(categoryRequest.getImage()))
                .build()).toList();
        return goodsCategoryRepository.saveAllAndFlush(goodsCategories);
    }

    @Transactional(readOnly = true)
    @Override
    public GoodsCategory getById(String id) {
        Optional<GoodsCategory> goodsCategory = goodsCategoryRepository.findById(id);
        return goodsCategory.orElseThrow(() -> new RuntimeException("Goods Category not found"));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GoodsCategory getOrSave(String name) {
        return goodsCategoryRepository.findByCategoryName(name)
                .orElseGet(() -> goodsCategoryRepository.saveAndFlush(
                        GoodsCategory.builder()
                                .categoryName(name)
                                .build()
                ));
    }

    @Transactional(readOnly = true)
    @Override
    public Long count() {
        return goodsCategoryRepository.count();
    }
}
