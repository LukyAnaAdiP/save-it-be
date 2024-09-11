package com.enigma.group5.save_it_backend.repository;

import com.enigma.group5.save_it_backend.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, String > {
    Optional<UserAccount> findByUsername(String username);
}
