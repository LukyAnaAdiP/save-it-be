package com.enigma.group5.save_it_backend.repository;

import com.enigma.group5.save_it_backend.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, String> {
}
