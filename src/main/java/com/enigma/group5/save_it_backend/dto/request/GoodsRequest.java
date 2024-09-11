package com.enigma.group5.save_it_backend.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsRequest {
    private String goodsName;
    private String goodsCategory;
    private String goodsDescription;
    private MultipartFile goodsImage;
}
