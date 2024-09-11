package com.enigma.group5.save_it_backend.service.Impl;

import com.enigma.group5.save_it_backend.dto.request.GoodsRequest;
import com.enigma.group5.save_it_backend.dto.request.GoodsUpdateRequest;
import com.enigma.group5.save_it_backend.dto.response.GoodsResponse;
import com.enigma.group5.save_it_backend.dto.response.ImageResponse;
import com.enigma.group5.save_it_backend.entity.Goods;
import com.enigma.group5.save_it_backend.entity.GoodsCategory;
import com.enigma.group5.save_it_backend.repository.GoodsRepository;
import com.enigma.group5.save_it_backend.service.GoodsCategoryService;
import com.enigma.group5.save_it_backend.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    private final GoodsRepository goodsRepository;
    private final GoodsCategoryService goodsCategoryService;

    @Override
    public List<GoodsRequest> parseToGoodsRequest(MultipartHttpServletRequest request) {

        List<GoodsRequest> goodsRequestList = new ArrayList<>();

        int i = 0;
        while (true) {

            if (request.getFileNames().hasNext()) {
                GoodsRequest goodsRequest = GoodsRequest.builder()
                        .goodsName(request.getParameter("goods[" + i + "].goodsName"))
                        .goodsCategory(request.getParameter("goods[" + i + "].goodsCategory"))
                        .goodsDescription(request.getParameter("goods[" + i + "].goodsDescription"))
                        .goodsImage(request.getFile("goods[" + i + "].goodsImage"))
                        .build();
                goodsRequestList.add(goodsRequest);
                i++;

            } else {
                break;
            }

        }

        return goodsRequestList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Goods create(Goods goods) {
        return goodsRepository.saveAndFlush(goods);
    }

    @Override
    public Goods getById(String id) {
        Optional<Goods> goods = goodsRepository.findById(id);
        return goods.orElseThrow(() -> new RuntimeException("Goods not found"));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GoodsResponse update(GoodsUpdateRequest goodsUpdateRequest) {

        Goods currentGoods = getById(goodsUpdateRequest.getGoodsId());
        GoodsCategory goodsCategory = goodsCategoryService.getById(goodsUpdateRequest.getGoodsCategoryId());

        if (goodsUpdateRequest.getGoodsName() != null) {
            currentGoods.setGoodsName(goodsUpdateRequest.getGoodsName());
        }
        if (goodsUpdateRequest.getGoodsCategoryId() != null) {
            currentGoods.setGoodsCategory(goodsCategory);
        }
        if (goodsUpdateRequest.getGoodsDescription() != null) {
            currentGoods.setGoodsDescription(goodsUpdateRequest.getGoodsDescription());
        }

        goodsRepository.saveAndFlush(currentGoods);

        return GoodsResponse.builder()
                .goodsName(currentGoods.getGoodsName())
                .goodsCategory(goodsCategory.getCategoryName())
                .goodsDescription(currentGoods.getGoodsDescription())
                .imageResponse(ImageResponse.builder()
                        .name(currentGoods.getGoodsImage().getName())
                        .url(currentGoods.getGoodsImage().getPath())
                        .build())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Goods> createBulk(List<Goods> goods) {
        return goodsRepository.saveAllAndFlush(goods);
    }

    @Override
    public void delete(String id) {
        goodsRepository.deleteById(id);
    }
}
