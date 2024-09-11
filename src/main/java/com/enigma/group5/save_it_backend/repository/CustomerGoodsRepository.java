package com.enigma.group5.save_it_backend.repository;

import com.enigma.group5.save_it_backend.entity.CustomerGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerGoodsRepository extends JpaRepository<CustomerGoods, String>, JpaSpecificationExecutor<CustomerGoods> {
    Optional<List<CustomerGoods>> findAllByCustomerId(String customerId);
    Optional<List<CustomerGoods>> findAllByWarehouseId(String warehouseId);
}
