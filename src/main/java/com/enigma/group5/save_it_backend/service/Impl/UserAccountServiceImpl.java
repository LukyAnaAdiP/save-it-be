package com.enigma.group5.save_it_backend.service.Impl;

import com.enigma.group5.save_it_backend.entity.UserAccount;
import com.enigma.group5.save_it_backend.repository.UserAccountRepository;
import com.enigma.group5.save_it_backend.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
    private final UserAccountRepository userAccountRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public UserAccount getByUserId(String id) {
        return userAccountRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found")
        );
    }

    @Override
    public UserAccount getByUsername(String username) {
        return userAccountRepository.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found")
        );
    }


    @Override
    public UserAccount getByContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userAccountRepository.findByUsername(authentication.getPrincipal().toString()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found from context")
        );
    }
}
