package com.enigma.group5.save_it_backend.dto.response;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllProductByVendorResponse {
    private VendorResponse vendorDetails;
    private List<ProductResponse> products;
}
