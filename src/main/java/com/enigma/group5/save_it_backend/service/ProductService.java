package com.enigma.group5.save_it_backend.service;

import com.enigma.group5.save_it_backend.dto.request.NewProductRequest;
import com.enigma.group5.save_it_backend.dto.request.ProductVendorRequest;
import com.enigma.group5.save_it_backend.dto.request.SearchProductRequest;
import com.enigma.group5.save_it_backend.dto.response.NewProductResponse;
import com.enigma.group5.save_it_backend.dto.response.ProductResponse;
import com.enigma.group5.save_it_backend.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductResponse create(NewProductRequest productRequest);
    List<NewProductResponse> createBulk(List<ProductVendorRequest> productVendorRequests);
    void createBulkInit(List<NewProductRequest> productRequests);
    Product getById(String id);
    Page<Product> getAll(SearchProductRequest productRequest);
    Product update(Product product);
    void deleteById(String id);
    Product findByName(String name);
    Long count();
}
