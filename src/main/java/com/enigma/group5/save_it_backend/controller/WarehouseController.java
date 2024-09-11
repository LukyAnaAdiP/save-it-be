package com.enigma.group5.save_it_backend.controller;

import com.enigma.group5.save_it_backend.constant.APIUrl;
import com.enigma.group5.save_it_backend.constant.ResponseMessage;
import com.enigma.group5.save_it_backend.dto.request.*;
import com.enigma.group5.save_it_backend.dto.response.*;
import com.enigma.group5.save_it_backend.entity.TransactionProduct;
import com.enigma.group5.save_it_backend.entity.VendorProduct;
import com.enigma.group5.save_it_backend.service.TransactionProductService;
import com.enigma.group5.save_it_backend.service.VendorProductService;
import com.enigma.group5.save_it_backend.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.WAREHOUSE_API)
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<WarehouseResponse>>> getAllWarehouse(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "vpId") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "cratedAt", required = false) String cratedAt
    ){
        SearchWarehouseRequest request = SearchWarehouseRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .createdAt(cratedAt)
                .build();

        Page<WarehouseResponse> warehouseResponses = warehouseService.getAllData(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(warehouseResponses.getTotalPages())
                .totalElements(warehouseResponses.getTotalElements())
                .page(warehouseResponses.getPageable().getPageNumber())
                .size(warehouseResponses.getPageable().getPageSize())
                .hasNext(warehouseResponses.hasNext())
                .hasPrevious(warehouseResponses.hasPrevious())
                .build();

        CommonResponse<List<WarehouseResponse>> response = CommonResponse.<List<WarehouseResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(warehouseResponses.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping(path = "/customer")
    public ResponseEntity<CommonResponse<List<WarehouseResponse>>> getAllWarehouseBasedOnCustomer(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "vpId") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "cratedAt", required = false) String cratedAt
    ){
        SearchWarehouseRequest request = SearchWarehouseRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .createdAt(cratedAt)
                .build();

        Page<WarehouseResponse> warehouseResponses = warehouseService.getAllDataBasedOnCustomer(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(warehouseResponses.getTotalPages())
                .totalElements(warehouseResponses.getTotalElements())
                .page(warehouseResponses.getPageable().getPageNumber())
                .size(warehouseResponses.getPageable().getPageSize())
                .hasNext(warehouseResponses.hasNext())
                .hasPrevious(warehouseResponses.hasPrevious())
                .build();

        CommonResponse<List<WarehouseResponse>> response = CommonResponse.<List<WarehouseResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(warehouseResponses.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping
    public ResponseEntity<CommonResponse<?>> updateWarehouse(
            @RequestBody WarehouseUpdateRequest request
            ){

        WarehouseUpdateResponse response = warehouseService.update(request);

        CommonResponse<?> commonResponse = CommonResponse.<Object>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .data(response)
                .build();
        return ResponseEntity.ok(commonResponse);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping
    public ResponseEntity<CommonResponse<?>> deleteWarehouse(
            @RequestBody WarehouseDeleteRequest request
    ){
        warehouseService.delete(request);
        CommonResponse<?> commonResponse = CommonResponse.<Object>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_DELETE_DATA)
                .build();
        return ResponseEntity.ok(commonResponse);
    }
}
