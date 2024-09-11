package com.enigma.group5.save_it_backend.repository;

import com.enigma.group5.save_it_backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
    // query method
    List<Product> findAllByProductNameLike(String name);
    Optional<Product> findByProductName(String name);
}
