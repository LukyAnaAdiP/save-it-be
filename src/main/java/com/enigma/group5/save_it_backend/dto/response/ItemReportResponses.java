package com.enigma.group5.save_it_backend.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemReportResponses {
    private ProductResponse productDetails;
    private VendorResponse vendorDetails;
    private Integer quantity;
    private Long price;
    private Long totalPricePerItem;
}
