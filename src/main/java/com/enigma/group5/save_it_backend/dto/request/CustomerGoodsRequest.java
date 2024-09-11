package com.enigma.group5.save_it_backend.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerGoodsRequest {
    private String goodsName;
    private String goodsCategoryId;
    private String goodsDescription;
    private MultipartFile goodsImage;
    private Long price;
    private Integer stocks;
}

/*
    customerGoodsRequests[0].goodsName : mouse,
    customerGoodsRequests[0].goodsCategoryId : 63f52d5f-dc26-4f57-b9c8-3d26edf61e14,
    customerGoodsRequests[0].goodsDescription : Lorem ipsum dolor sit amet consectetur adipisicing elit. Veniam aspernatur quos repudiandae necessitatibus est veritatis, ratione sed doloribus quia minus maxime facere amet perspiciatis eos!,
    customerGoodsRequests[0].goodsImage : ,
    customerGoodsRequests[0].price : 500000,
    customerGoodsRequests[0].stocks : 10

    customerGoodsRequests[1].goodsName : keyboard,
    customerGoodsRequests[1].goodsCategoryId : 63f52d5f-dc26-4f57-b9c8-3d26edf61e14,
    customerGoodsRequests[1].goodsDescription : Lorem ipsum dolor sit amet consectetur adipisicing elit. Veniam aspernatur quos repudiandae necessitatibus est veritatis, ratione sed doloribus quia minus maxime facere amet perspiciatis eos!
    customerGoodsRequests[1].goodsImage : ,
    customerGoodsRequests[1].price : 2000000000,
    customerGoodsRequests[1].stocks : 20

*/
