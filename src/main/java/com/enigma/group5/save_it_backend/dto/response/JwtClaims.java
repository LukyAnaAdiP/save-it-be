package com.enigma.group5.save_it_backend.dto.response;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtClaims {
    // ini adalah data yg kita dapatkan dari Payload: Data Jwtnya
    private String userAccountId;
    private List<String> roles;
}

