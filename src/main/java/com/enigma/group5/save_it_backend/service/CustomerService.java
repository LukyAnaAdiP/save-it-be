package com.enigma.group5.save_it_backend.service;

import com.enigma.group5.save_it_backend.dto.request.CustomerRequest;
import com.enigma.group5.save_it_backend.dto.request.SearchCustomerRequest;
import com.enigma.group5.save_it_backend.dto.response.CustomerListResponse;
import com.enigma.group5.save_it_backend.dto.response.CustomerResponse;
import com.enigma.group5.save_it_backend.entity.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {

    void create (Customer customer);
    Page<CustomerListResponse> getAll(SearchCustomerRequest searchCustomerRequest);
    Customer getById(String id);
    Customer getByUsername(String username);
    CustomerResponse update(String username, CustomerRequest customerRequest);
    void deleteById(String username);
}
