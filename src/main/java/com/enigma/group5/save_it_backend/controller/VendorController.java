package com.enigma.group5.save_it_backend.controller;

import com.enigma.group5.save_it_backend.constant.APIUrl;
import com.enigma.group5.save_it_backend.constant.ResponseMessage;
import com.enigma.group5.save_it_backend.dto.request.NewVendorRequest;
import com.enigma.group5.save_it_backend.dto.request.SearchVendorRequest;
import com.enigma.group5.save_it_backend.dto.response.CommonResponse;
import com.enigma.group5.save_it_backend.dto.response.PagingResponse;
import com.enigma.group5.save_it_backend.entity.Vendor;
import com.enigma.group5.save_it_backend.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.VENDOR_API)
public class VendorController {
    private final VendorService vendorService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<CommonResponse<Vendor>> createNewVendor(@RequestBody NewVendorRequest vendorRequest) {
        Vendor newVendor = vendorService.create(vendorRequest);
        CommonResponse<Vendor> response = CommonResponse.<Vendor>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ResponseMessage.SUCCESS_SAVE_DATA)
                .data(newVendor)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = APIUrl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<Vendor>> getById(@PathVariable String id) {
        Vendor vendor = vendorService.getById(id);
        CommonResponse<Vendor> response = CommonResponse.<Vendor>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(vendor)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<Vendor>>> getAllVendor(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "vendorName") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "vendorName", required = false) String vendorName
    ) {
        SearchVendorRequest request = SearchVendorRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .name(vendorName)
                .build();

        Page<Vendor> vendorAll = vendorService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(vendorAll.getTotalPages())
                .totalElements(vendorAll.getTotalElements())
                .page(vendorAll.getPageable().getPageNumber())
                .size(vendorAll.getPageable().getPageSize())
                .hasNext(vendorAll.hasNext())
                .hasPrevious(vendorAll.hasPrevious())
                .build();

        CommonResponse<List<Vendor>> response = CommonResponse.<List<Vendor>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(vendorAll.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<Vendor>> updateVendor(@RequestBody Vendor vendor) {
        Vendor update = vendorService.update(vendor);
        CommonResponse<Vendor> response = CommonResponse.<Vendor>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .data(update)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = APIUrl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<?>> deleteById(@PathVariable String id) {
        vendorService.deleteById(id);
        CommonResponse<Vendor> response = CommonResponse.<Vendor>builder()
                .statusCode(HttpStatus.OK.value())
                .message("OK, Success Delete Vendor with id " + id)
                .build();
        return ResponseEntity.ok(response);
    }
}
