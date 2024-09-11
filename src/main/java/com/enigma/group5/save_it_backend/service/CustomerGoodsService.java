package com.enigma.group5.save_it_backend.service;

import com.enigma.group5.save_it_backend.dto.request.CustomerGoodsRequest;
import com.enigma.group5.save_it_backend.dto.request.CustomerGoodsUpdateRequest;
import com.enigma.group5.save_it_backend.dto.request.SearchCustomerGoodsRequest;
import com.enigma.group5.save_it_backend.dto.request.WarehouseDeleteRequest;
import com.enigma.group5.save_it_backend.dto.response.CustomerGoodsResponse;
import com.enigma.group5.save_it_backend.entity.Customer;
import com.enigma.group5.save_it_backend.entity.CustomerGoods;
import com.enigma.group5.save_it_backend.entity.Warehouse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerGoodsService {

    CustomerGoodsResponse create (List<CustomerGoodsRequest> customerGoodsRequest);
    Page<CustomerGoodsResponse> getAll (SearchCustomerGoodsRequest searchCustomerGoodsRequest);
    Page<CustomerGoodsResponse> getAllByCustomer (SearchCustomerGoodsRequest searchCustomerGoodsRequest);
    CustomerGoods getById (String id);
    CustomerGoodsResponse update(CustomerGoodsUpdateRequest customerGoodsUpdateRequest);
    Long countByCustomer (Customer customer);
    List<CustomerGoods> getAllByWarehouse (Warehouse warehouse);
    void deleteById (String id);

}
