package com.enigma.group5.save_it_backend.controller;

import com.enigma.group5.save_it_backend.constant.APIUrl;
import com.enigma.group5.save_it_backend.constant.ResponseMessage;
import com.enigma.group5.save_it_backend.dto.request.CustomerRequest;
import com.enigma.group5.save_it_backend.dto.request.SearchCustomerRequest;
import com.enigma.group5.save_it_backend.dto.response.*;
import com.enigma.group5.save_it_backend.entity.Customer;
import com.enigma.group5.save_it_backend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.CUSTOMER)
public class CustomerController {

    private final CustomerService customerService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping(path = "/all")
    public ResponseEntity<CommonResponse<List<CustomerListResponse>>> getAll(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "username", required = false) String username

    ){
        SearchCustomerRequest request = SearchCustomerRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .username(username)
                .build();

        Page<CustomerListResponse> customerListResponses = customerService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(customerListResponses.getTotalPages())
                .totalElements(customerListResponses.getTotalElements())
                .page(customerListResponses.getPageable().getPageNumber())
                .size(customerListResponses.getPageable().getPageSize())
                .hasNext(customerListResponses.hasNext())
                .hasPrevious(customerListResponses.hasPrevious())
                .build();

        CommonResponse<List<CustomerListResponse>> response = CommonResponse.<List<CustomerListResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(customerListResponses.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping
    public ResponseEntity<CommonResponse<CustomerResponse>> getCustomerByUsername(){

        Customer customer = customerService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        CustomerResponse customerResponse = CustomerResponse.builder()
                .username(customer.getUserAccount().getUsername())
                .fullNameCustomer(customer.getFullNameCustomer())
                .emailCustomer(customer.getEmailCustomer())
                .phoneCustomer(customer.getPhoneCustomer())
                .addressCustomer(customer.getAddressCustomer())
                .customerImage(
                        ImageResponse.builder()
                                .name(customer.getCustomerImage() != null ? customer.getCustomerImage().getName() : null)
                                .url(customer.getCustomerImage() != null ? customer.getCustomerImage().getPath() : null)
                                .build()
                )
                .build();

        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(customerResponse)
                .build();
        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping
    public ResponseEntity<CommonResponse<CustomerResponse>> update(
            @RequestPart (name = "name") String name,
            @RequestPart (name = "email") String email,
            @RequestPart (name = "phone") String phone,
            @RequestPart (name = "address") String address,
            @RequestPart (name = "image", required = false) MultipartFile image
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        CustomerRequest customerRequest = CustomerRequest.builder()
                .fullNameCustomer(name)
                .emailCustomer(email)
                .phoneCustomer(phone)
                .addressCustomer(address)
                .imageCustomer(image != null ? image : null)
                .build();

        CustomerResponse customerResponse = customerService.update(username, customerRequest);

        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .data(customerResponse)
                .build();

        return ResponseEntity.ok(response);
    }

}
