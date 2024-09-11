package com.enigma.group5.save_it_backend.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private String id;
    private String name;
    private String category;
    private String description;
    private ImageResponse image;
}