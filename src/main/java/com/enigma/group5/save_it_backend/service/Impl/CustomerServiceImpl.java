package com.enigma.group5.save_it_backend.service.Impl;

import com.enigma.group5.save_it_backend.constant.DirectoryImage;
import com.enigma.group5.save_it_backend.dto.request.CustomerRequest;
import com.enigma.group5.save_it_backend.dto.request.SearchCustomerRequest;
import com.enigma.group5.save_it_backend.dto.response.CustomerGoodsResponse;
import com.enigma.group5.save_it_backend.dto.response.CustomerListResponse;
import com.enigma.group5.save_it_backend.dto.response.CustomerResponse;
import com.enigma.group5.save_it_backend.dto.response.ImageResponse;
import com.enigma.group5.save_it_backend.entity.Customer;
import com.enigma.group5.save_it_backend.entity.Image;
import com.enigma.group5.save_it_backend.entity.UserAccount;
import com.enigma.group5.save_it_backend.repository.CustomerRepository;
import com.enigma.group5.save_it_backend.service.CustomerGoodsService;
import com.enigma.group5.save_it_backend.service.CustomerService;
import com.enigma.group5.save_it_backend.service.TransactionProductService;
import com.enigma.group5.save_it_backend.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserAccountService userAccountService;
    private final ImageServiceImpl imageService;

    private final ApplicationContext transactionProductService;
    private final ApplicationContext customerGoodsService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(Customer customer) {
        customerRepository.save(customer);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CustomerListResponse> getAll(SearchCustomerRequest searchCustomerRequest) {

        if (searchCustomerRequest.getPage() <= 0){
            searchCustomerRequest.setPage(1);
        }
        String validSortBy;
        if ("fullNameCustomer".equalsIgnoreCase(searchCustomerRequest.getSortBy()) || "emailCustomer".equalsIgnoreCase(searchCustomerRequest.getSortBy())){
            validSortBy = searchCustomerRequest.getSortBy();
        }else {
            validSortBy = "fullNameCustomer";
        }

        Sort sort = Sort.by(Sort.Direction.fromString(searchCustomerRequest.getDirection()),  validSortBy);

        Pageable pageable = PageRequest.of((searchCustomerRequest.getPage() -1), searchCustomerRequest.getSize(), sort);

        List<CustomerListResponse> customerListResponses = customerRepository.findAll().stream().map(
                customer -> {
                    CustomerResponse customerResponse = parseCustomerToCustomerResponse(customer);

                    Long customerGoodsTotal = getCustomerGoodsService().countByCustomer(customer);
                    Long transactionProductTotal = getTransactionProductService().countByCustomer(customer);


                    return CustomerListResponse.builder()
                            .customerDetails(customerResponse)
                            .totalItem(customerGoodsTotal + transactionProductTotal)
                            .build();
                }
        ).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), customerListResponses.size());

        List<CustomerListResponse> pageContent = customerListResponses.subList(start, end);

        return new PageImpl<>(pageContent, pageable, customerListResponses.size());

    }
    @Transactional(readOnly = true)
    @Override
    public Customer getById(String id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        return optionalCustomer.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public Customer getByUsername(String username) {

        UserAccount userAccount = userAccountService.getByUsername(username);

        return customerRepository.findByUserAccountId(userAccount.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found"));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerResponse update(String username, CustomerRequest customerRequest) {

        Customer currentCustomer = getByUsername(username);

        currentCustomer.setFullNameCustomer(customerRequest.getFullNameCustomer());
        currentCustomer.setEmailCustomer(customerRequest.getEmailCustomer());
        currentCustomer.setPhoneCustomer(customerRequest.getPhoneCustomer());
        currentCustomer.setAddressCustomer(customerRequest.getAddressCustomer());

        if(!customerRequest.getImageCustomer().isEmpty()){

            Image customerImage;

            if(currentCustomer.getCustomerImage() == null){
                customerImage = imageService.saveToCloud(customerRequest.getImageCustomer(), DirectoryImage.CUSTOMER);
            } else {
                customerImage = imageService.updateToCloud(customerRequest.getImageCustomer(), DirectoryImage.CUSTOMER, currentCustomer.getCustomerImage().getId());
            }

            currentCustomer.setCustomerImage(customerImage);

        } else {
            currentCustomer.setCustomerImage(null);
        }

        customerRepository.save(currentCustomer);

        return parseCustomerToCustomerResponse(currentCustomer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String username) {
        UserAccount userAccount = userAccountService.getByUsername(username);
        customerRepository.deleteById(userAccount.getId());
    }

    private CustomerResponse parseCustomerToCustomerResponse(Customer customer){
        return CustomerResponse.builder()
                .username(customer.getUserAccount().getUsername())
                .fullNameCustomer(customer.getFullNameCustomer())
                .emailCustomer(customer.getEmailCustomer())
                .phoneCustomer(customer.getPhoneCustomer())
                .addressCustomer(customer.getAddressCustomer())
                .customerImage(ImageResponse.builder()
                        .name(customer.getCustomerImage() != null ? customer.getCustomerImage().getName() : null)
                        .url(customer.getCustomerImage() != null ? customer.getCustomerImage().getPath() : null)
                        .build())
                .build();
    }

    private TransactionProductService getTransactionProductService(){
        return transactionProductService.getBean(TransactionProductService.class);
    }
    private CustomerGoodsService getCustomerGoodsService(){
        return customerGoodsService.getBean(CustomerGoodsService.class);
    }

}
