package com.enigma.group5.save_it_backend.utils;

import com.enigma.group5.save_it_backend.dto.request.NewProductRequest;
import com.enigma.group5.save_it_backend.dto.request.ProductVendorRequest;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductVendorRequestListUtil {
    private List<ProductVendorRequest> productVendorRequests;
}
