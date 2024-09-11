package com.enigma.group5.save_it_backend.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductVendorRequest {
    private String vendorId;
    private String productName;
    private String productDescription;
    private String productCategoryId;
    private MultipartFile productImage;
    private Long price;
    private Integer stocks;
}
