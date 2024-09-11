package com.enigma.group5.save_it_backend.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewProductResponse {
    private String id;
    private String name;
    private String vendorName;
    private String category;
    private String description;
    private ImageResponse image;
    private Long price;
    private Integer stocks;
}
