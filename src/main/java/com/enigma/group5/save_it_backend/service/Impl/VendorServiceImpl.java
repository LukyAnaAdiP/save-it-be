package com.enigma.group5.save_it_backend.service.Impl;

import com.enigma.group5.save_it_backend.dto.request.NewVendorRequest;
import com.enigma.group5.save_it_backend.dto.request.SearchVendorRequest;
import com.enigma.group5.save_it_backend.entity.Vendor;
import com.enigma.group5.save_it_backend.repository.VendorRepository;
import com.enigma.group5.save_it_backend.service.VendorService;
import com.enigma.group5.save_it_backend.specification.VendorSpecification;
import com.enigma.group5.save_it_backend.utils.ValidationUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Vendor create(NewVendorRequest vendorRequest) {
        validationUtil.validate(vendorRequest);
        Vendor newVendor = Vendor.builder()
                .vendorName(vendorRequest.getName())
                .vendorPhone(vendorRequest.getMobilePhoneNo())
                .vendorAddress(vendorRequest.getAddress())
                .vendorEmail(vendorRequest.getEmail())
                .build();
        return vendorRepository.saveAndFlush(newVendor);
    }

    @Override
    public List<Vendor> showAll() {
        return vendorRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Vendor getById(String id) {
        Optional<Vendor> optionalVendor = vendorRepository.findById(id);
        if (optionalVendor.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "vendor not found");
        }
        return optionalVendor.get();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Vendor> getAll(SearchVendorRequest vendorRequest) {
        if (vendorRequest.getPage() <= 0){
            vendorRequest.setPage(1);
        }
        String validSortBy;
        if ("vendorName".equalsIgnoreCase(vendorRequest.getSortBy())){
            validSortBy = vendorRequest.getSortBy();
        }else {
            validSortBy = "vendorName";
        }

        Sort sort = Sort.by(Sort.Direction.fromString(vendorRequest.getDirection()), validSortBy);

        Pageable pageable = PageRequest.of((vendorRequest.getPage() -1), vendorRequest.getSize(), sort);

        Specification<Vendor> specification = VendorSpecification.getSpecification(vendorRequest);

        return vendorRepository.findAll(specification,pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Vendor update(Vendor vendor) {
        getById(vendor.getId());
        return vendorRepository.saveAndFlush(vendor);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        Vendor currentVendor = getById(id);
        vendorRepository.delete(currentVendor);
    }

    @Transactional(readOnly = true)
    @Override
    public Vendor findByName(String name) {
        return vendorRepository.findByVendorName(name)
                .orElseThrow(() -> new RuntimeException("Vendor not found with name: " + name));
    }

    @Transactional(readOnly = true)
    @Override
    public Long count() {
        return vendorRepository.count();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createBulk(List<NewVendorRequest> vendorRequests) {
        List<Vendor> vendors = new ArrayList<>();
        for (NewVendorRequest vendorRequest : vendorRequests) {
            validationUtil.validate(vendorRequest);
            vendors.add(Vendor.builder()
                    .vendorName(vendorRequest.getName())
                    .vendorPhone(vendorRequest.getMobilePhoneNo())
                    .vendorAddress(vendorRequest.getAddress())
                    .vendorEmail(vendorRequest.getEmail())
                    .build()
            );
        }
        vendorRepository.saveAll(vendors);
    }
}
