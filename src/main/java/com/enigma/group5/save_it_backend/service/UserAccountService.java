package com.enigma.group5.save_it_backend.service;

import com.enigma.group5.save_it_backend.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAccountService extends UserDetailsService {
    UserAccount getByUserId(String id);

    UserAccount getByUsername(String username);

    UserAccount getByContext();
}
