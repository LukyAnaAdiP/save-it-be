package com.enigma.group5.save_it_backend.service;

import com.enigma.group5.save_it_backend.dto.request.GoodsRequest;
import com.enigma.group5.save_it_backend.dto.request.GoodsUpdateRequest;
import com.enigma.group5.save_it_backend.dto.response.GoodsResponse;
import com.enigma.group5.save_it_backend.entity.Goods;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface GoodsService {
    List<GoodsRequest> parseToGoodsRequest(MultipartHttpServletRequest request);
    Goods create(Goods goods);
    Goods getById(String id);
    GoodsResponse update(GoodsUpdateRequest goodsUpdateRequest);
    List<Goods> createBulk(List<Goods> goods);
    void delete(String id);
}
