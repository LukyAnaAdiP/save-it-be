package com.enigma.group5.save_it_backend.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {
    private String fullNameCustomer;
    private String emailCustomer;
    private String phoneCustomer;
    private String addressCustomer;
    private MultipartFile imageCustomer;
}
