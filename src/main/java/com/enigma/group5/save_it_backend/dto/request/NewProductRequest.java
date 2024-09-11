package com.enigma.group5.save_it_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewProductRequest {
    @NotBlank(message = "Name is Required")
    private String name;

    @NotBlank(message = "Category is Required")
    private String category;

    @NotBlank(message = "Description is Required")
    private String description;

    private MultipartFile image;
}
