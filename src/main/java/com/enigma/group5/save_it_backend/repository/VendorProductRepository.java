package com.enigma.group5.save_it_backend.repository;

import com.enigma.group5.save_it_backend.entity.VendorProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorProductRepository extends JpaRepository<VendorProduct, String> {
    Optional<List<VendorProduct>> findAllByVendorId(String vendorId);
}
