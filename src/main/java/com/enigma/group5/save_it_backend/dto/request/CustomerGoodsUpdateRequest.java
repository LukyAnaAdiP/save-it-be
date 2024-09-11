package com.enigma.group5.save_it_backend.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerGoodsUpdateRequest {
    private String customerGoodsId;
    private String goodsName;
    private String goodsCategoryId;
    private String goodsDescription;
    private Long price;
    private Integer stocks;
}
