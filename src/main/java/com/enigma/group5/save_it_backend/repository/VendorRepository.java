package com.enigma.group5.save_it_backend.repository;

import com.enigma.group5.save_it_backend.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, String>, JpaSpecificationExecutor<Vendor>  {
    List<Vendor> findByVendorNameLike(String name);
    Optional<Vendor> findByVendorName(String name);
}