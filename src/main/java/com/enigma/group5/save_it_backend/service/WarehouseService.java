package com.enigma.group5.save_it_backend.service;

import com.enigma.group5.save_it_backend.dto.request.SearchWarehouseRequest;
import com.enigma.group5.save_it_backend.dto.request.WarehouseDeleteRequest;
import com.enigma.group5.save_it_backend.dto.request.WarehouseUpdateRequest;
import com.enigma.group5.save_it_backend.dto.response.WarehouseGoodsResponse;
import com.enigma.group5.save_it_backend.dto.response.WarehouseResponse;
import com.enigma.group5.save_it_backend.dto.response.WarehouseUpdateResponse;
import com.enigma.group5.save_it_backend.entity.Warehouse;
import org.springframework.data.domain.Page;

public interface WarehouseService {
    Warehouse create (Warehouse warehouse);
    Page<WarehouseResponse> getAllData (SearchWarehouseRequest searchWarehouseRequest);
    Page<WarehouseResponse> getAllDataBasedOnCustomer(SearchWarehouseRequest searchWarehouseRequest);
    WarehouseUpdateResponse update(WarehouseUpdateRequest warehouseUpdateRequest);
    void delete(WarehouseDeleteRequest warehouseDeleteRequest);
}
