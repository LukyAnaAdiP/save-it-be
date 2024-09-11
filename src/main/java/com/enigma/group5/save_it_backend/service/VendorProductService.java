package com.enigma.group5.save_it_backend.service;

import com.enigma.group5.save_it_backend.dto.request.SearchVendorProductRequest;
import com.enigma.group5.save_it_backend.dto.request.UpdateStocksVendorProductRequest;
import com.enigma.group5.save_it_backend.dto.response.GetAllProductByVendorResponse;
import com.enigma.group5.save_it_backend.dto.response.VendorProductResponse;
import com.enigma.group5.save_it_backend.entity.VendorProduct;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VendorProductService {

    void create(VendorProduct vendorProduct);
    Page<VendorProductResponse> getAll(SearchVendorProductRequest vendorProductRequest);
    Page<GetAllProductByVendorResponse> getAllProductByVendor(SearchVendorProductRequest vendorProductRequest);
    VendorProduct getById(String id);
    VendorProduct updateStocks(UpdateStocksVendorProductRequest updateStocksVendorProductRequest);
    Long count();
    void createBulk(List<VendorProduct> vendorProducts);
}
