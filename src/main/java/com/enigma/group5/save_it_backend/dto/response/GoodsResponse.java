package com.enigma.group5.save_it_backend.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsResponse {
    private String goodsName;
    private String goodsCategory;
    private String goodsDescription;
    private ImageResponse imageResponse;
}
