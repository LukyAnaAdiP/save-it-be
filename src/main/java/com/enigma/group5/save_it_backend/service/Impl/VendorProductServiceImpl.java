package com.enigma.group5.save_it_backend.service.Impl;

import com.enigma.group5.save_it_backend.constant.APIUrl;
import com.enigma.group5.save_it_backend.dto.request.SearchVendorProductRequest;
import com.enigma.group5.save_it_backend.dto.request.SearchVendorRequest;
import com.enigma.group5.save_it_backend.dto.request.UpdateStocksVendorProductRequest;
import com.enigma.group5.save_it_backend.dto.response.*;
import com.enigma.group5.save_it_backend.entity.*;
import com.enigma.group5.save_it_backend.repository.VendorProductRepository;
import com.enigma.group5.save_it_backend.service.ImageService;
import com.enigma.group5.save_it_backend.service.ProductService;
import com.enigma.group5.save_it_backend.service.VendorProductService;
import com.enigma.group5.save_it_backend.service.VendorService;
import com.enigma.group5.save_it_backend.specification.CustomerGoodsSpecification;
import com.enigma.group5.save_it_backend.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class VendorProductServiceImpl implements VendorProductService {

    private final VendorProductRepository vendorProductRepository;
    private final VendorService vendorService;
    private final ProductService productService;
    private final ImageService imageService;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(VendorProduct vendorProduct) {

        validationUtil.validate(vendorProduct);

        vendorProductRepository.save(vendorProduct);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<VendorProductResponse> getAll(SearchVendorProductRequest vendorProductRequest) {
        if (vendorProductRequest.getPage() <= 0){
            vendorProductRequest.setPage(1);
        }

        String validSortBy;
        if ("vendorName".equalsIgnoreCase(vendorProductRequest.getSortBy()) || "productName".equalsIgnoreCase(vendorProductRequest.getSortBy())
                || "priceName".equals(vendorProductRequest.getSortBy())) {
            validSortBy = vendorProductRequest.getSortBy();
        }
        else {
            validSortBy = "vendorId";
        }

        Sort sort = Sort.by(Sort.Direction.fromString(vendorProductRequest.getDirection()), validSortBy);

        Pageable pageable = PageRequest.of(vendorProductRequest.getPage() - 1, vendorProductRequest.getSize(), sort);

        List<VendorProductResponse> responses = vendorProductRepository.findAll().stream().map(vendorProduct -> {
            try {
                return parseToVendorProductResponse(vendorProduct);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), responses.size());

        List<VendorProductResponse> pageContent = responses.subList(start, end);

        return new PageImpl<>(pageContent, pageable, responses.size());
    }

    @Override
    public Page<GetAllProductByVendorResponse> getAllProductByVendor(SearchVendorProductRequest vendorProductRequest) {

        if (vendorProductRequest.getPage() <= 0){
            vendorProductRequest.setPage(1);
        }

        Pageable pageable = PageRequest.of(vendorProductRequest.getPage() - 1, vendorProductRequest.getSize());

        //Find All Product By Vendor
        List<Vendor> getAllVendor = vendorService.showAll();
        List<GetAllProductByVendorResponse> allProductByVendorResponses = getAllVendor.stream().map(
                vendor -> {

                    List<VendorProduct> vendorProducts = vendorProductRepository.findAllByVendorId(vendor.getId()).orElseThrow(() -> new RuntimeException("Vendor Product not found"));

                    List<ProductResponse> productResponses = vendorProducts.stream().map(
                            product -> ProductResponse.builder()
                                    .id(product.getProduct().getId())
                                    .name(product.getProduct().getProductName())
                                    .category(product.getProduct().getProductCategory().getCategoryName())
                                    .description(product.getProduct().getProductDescription())
                                    .image(ImageResponse.builder()
                                            .name(product.getProduct().getProductImage().getName())
                                            .url(product.getProduct().getProductImage().getPath())
                                            .build())
                                    .build()
                    ).toList();

                    return GetAllProductByVendorResponse.builder()
                            .vendorDetails(VendorResponse.builder()
                                    .vendorId(vendor.getId())
                                    .vendorName(vendor.getVendorName())
                                    .vendorEmail(vendor.getVendorEmail())
                                    .vendorPhone(vendor.getVendorPhone())
                                    .vendorAddress(vendor.getVendorAddress())
                                    .build())
                            .products(productResponses)
                            .build();
                }).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allProductByVendorResponses.size());

        System.out.println("INI vendor product" + allProductByVendorResponses.size());

        List<GetAllProductByVendorResponse> pageContent = allProductByVendorResponses.subList(start, end);

        return new PageImpl<>(pageContent, pageable, allProductByVendorResponses.size());
    }

    @Transactional(readOnly = true)
    @Override
    public VendorProduct getById(String id) {
        Optional<VendorProduct> optionalVendorProduct = vendorProductRepository.findById(id);

        return optionalVendorProduct.orElseThrow(() -> new RuntimeException("Vendor product not found"));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public VendorProduct updateStocks(UpdateStocksVendorProductRequest vendorProduct) {

        VendorProduct currentVendorProduct = getById(vendorProduct.getVendorProductId());
        if (currentVendorProduct.getStocks() == 0) {
            throw new RuntimeException("Vendor product is empty");
        }else {
            currentVendorProduct.setStocks(currentVendorProduct.getStocks().intValue() - vendorProduct.getQuantity().intValue());
            System.out.println("Ini current product" + currentVendorProduct.getStocks());
            return vendorProductRepository.saveAndFlush(currentVendorProduct);
        }

    }

    @Transactional(readOnly = true)
    @Override
    public Long count() {
        return vendorProductRepository.count();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createBulk(List<VendorProduct> vendorProducts) {
        validationUtil.validate(vendorProducts);
        vendorProductRepository.saveAllAndFlush(vendorProducts);
    }

    private VendorProductResponse parseToVendorProductResponse(VendorProduct vendorProduct) throws IOException {
        Vendor vendor = vendorService.getById(vendorProduct.getVendor().getId());
        Product product = productService.getById(vendorProduct.getProduct().getId());
        Image image = imageService.searchById(product.getProductImage().getId());
        ImageResponse imageResponse = ImageResponse.builder()
                .name(image.getName())
                .url(image.getPath())
                .build();

        VendorProductResponse response = VendorProductResponse.builder()
                .vendorProductId(vendorProduct.getId())
                .vendorName(vendor.getVendorName())
                .vendorEmail(vendor.getVendorEmail())
                .vendorPhone(vendor.getVendorPhone())
                .vendorAddress(vendor.getVendorAddress())
                .productName(product.getProductName())
                .productCategory(product.getProductCategory().getCategoryName())
                .productDescription(product.getProductDescription())
                .productImage(imageResponse)
                .price(vendorProduct.getPrice())
                .stocks(vendorProduct.getStocks())
                .build();
        return response;
    }


}
