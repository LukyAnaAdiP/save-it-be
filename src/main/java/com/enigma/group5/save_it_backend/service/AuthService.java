package com.enigma.group5.save_it_backend.service;

import com.enigma.group5.save_it_backend.dto.request.AuthRequest;
import com.enigma.group5.save_it_backend.dto.request.RegisterRequest;
import com.enigma.group5.save_it_backend.dto.response.LoginResponse;
import com.enigma.group5.save_it_backend.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(AuthRequest request);
}
