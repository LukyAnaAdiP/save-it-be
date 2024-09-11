package com.enigma.group5.save_it_backend.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionProductsGoodsResponse {
    private String vendorProductId;
    private String vendorName;
    private String vendorEmail;
    private String vendorPhone;
    private String vendorAddress;
    private String productName;
    private String productCategory;
    private String productDescription;
    private ImageResponse productImage;
}
