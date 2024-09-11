package com.enigma.group5.save_it_backend.repository;

import com.enigma.group5.save_it_backend.entity.GoodsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoodsCategoryRepository extends JpaRepository<GoodsCategory, String> {
    Optional<GoodsCategory> findByCategoryName(String name);
}
