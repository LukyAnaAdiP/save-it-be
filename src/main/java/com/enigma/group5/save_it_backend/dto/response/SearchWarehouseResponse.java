package com.enigma.group5.save_it_backend.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchWarehouseResponse {
    private String vendorId;
    private String vendorName;
    private String vendorAddress;
    private String vendorPhone;
    private String productId;
    private String productName;
    private String category;
    private String vpId;
    private Long price;
    private Integer stock;
}
