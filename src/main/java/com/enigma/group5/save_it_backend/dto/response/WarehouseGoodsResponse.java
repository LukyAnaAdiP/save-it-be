package com.enigma.group5.save_it_backend.dto.response;

import com.enigma.group5.save_it_backend.entity.Vendor;
import com.enigma.group5.save_it_backend.entity.VendorProduct;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WarehouseGoodsResponse {
    private String vendorProductId;
    private VendorResponse vendorDetails;
    private String warehouseId;
    private String goodsName;
    private String goodsCategoryId;
    private String goodsCategoryName;
    private String goodsDescription;
    private ImageResponse goodsImage;
    private Long price;
    private Integer stocks;
}
