package com.enigma.group5.save_it_backend.repository;

import com.enigma.group5.save_it_backend.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetail, String> {
}
