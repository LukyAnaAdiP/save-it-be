package com.enigma.group5.save_it_backend.repository;

import com.enigma.group5.save_it_backend.entity.TransactionProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionProductRepository extends JpaRepository<TransactionProduct, String>, JpaSpecificationExecutor<TransactionProduct> {
    Optional<List<TransactionProduct>> findAllByVendorProductId(String vpId);
    Optional<List<TransactionProduct>> findAllByTransactionId(String transactionId);
    Optional<List<TransactionProduct>> findAllByWarehouseId(String warehouseId);
}
