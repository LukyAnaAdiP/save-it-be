package com.enigma.group5.save_it_backend.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {
    String id;
    String categoryName;
    ImageResponse imageResponse;
}
