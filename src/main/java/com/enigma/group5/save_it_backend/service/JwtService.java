package com.enigma.group5.save_it_backend.service;

import com.enigma.group5.save_it_backend.dto.response.JwtClaims;
import com.enigma.group5.save_it_backend.entity.UserAccount;

public interface JwtService {
    String generateToken(UserAccount userAccount);

    boolean verifyJwtToken(String token);

    JwtClaims getClaimsByToken(String token);
}

