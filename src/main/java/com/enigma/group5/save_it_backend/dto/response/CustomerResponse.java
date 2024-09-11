package com.enigma.group5.save_it_backend.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    private String username;
    private String fullNameCustomer;
    private String emailCustomer;
    private String phoneCustomer;
    private String addressCustomer;
    private ImageResponse customerImage;
}
