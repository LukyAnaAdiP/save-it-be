package com.enigma.group5.save_it_backend.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsUpdateRequest {

    private String goodsId;
    private String goodsName;
    private String goodsCategoryId;
    private String goodsDescription;

}
