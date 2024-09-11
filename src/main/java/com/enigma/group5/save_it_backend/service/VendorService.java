package com.enigma.group5.save_it_backend.service;

import com.enigma.group5.save_it_backend.dto.request.NewVendorRequest;
import com.enigma.group5.save_it_backend.dto.request.SearchVendorRequest;
import com.enigma.group5.save_it_backend.entity.Vendor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VendorService {
    Vendor create(NewVendorRequest vendorRequest);
    List<Vendor> showAll();
    Vendor getById(String id);
    Page<Vendor> getAll(SearchVendorRequest vendorRequest);
    Vendor update(Vendor vendor);
    void deleteById(String id);
    Vendor findByName(String name);
    Long count();
    void createBulk(List<NewVendorRequest> vendorRequests);
}
