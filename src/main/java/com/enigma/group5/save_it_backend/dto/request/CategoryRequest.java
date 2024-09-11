package com.enigma.group5.save_it_backend.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {
    private String productCategoryName;
    private MultipartFile image;
}
