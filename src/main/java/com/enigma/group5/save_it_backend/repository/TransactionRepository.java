package com.enigma.group5.save_it_backend.repository;

import com.enigma.group5.save_it_backend.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    Optional<List<Transaction>> findAllByCustomerId(String customerId);
}
