package com.enigma.group5.save_it_backend.repository;

import com.enigma.group5.save_it_backend.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository  extends JpaRepository<Warehouse, String>, JpaSpecificationExecutor<Warehouse> {
}
