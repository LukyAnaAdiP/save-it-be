package com.enigma.group5.save_it_backend.service.Impl;

import com.enigma.group5.save_it_backend.constant.APIUrl;
import com.enigma.group5.save_it_backend.constant.DirectoryImage;
import com.enigma.group5.save_it_backend.dto.request.NewProductRequest;
import com.enigma.group5.save_it_backend.dto.request.ProductVendorRequest;
import com.enigma.group5.save_it_backend.dto.request.SearchProductRequest;
import com.enigma.group5.save_it_backend.dto.response.ImageResponse;
import com.enigma.group5.save_it_backend.dto.response.NewProductResponse;
import com.enigma.group5.save_it_backend.dto.response.ProductResponse;
import com.enigma.group5.save_it_backend.entity.*;
import com.enigma.group5.save_it_backend.repository.ProductRepository;
import com.enigma.group5.save_it_backend.service.*;
import com.enigma.group5.save_it_backend.specification.ProductSpecification;
import com.enigma.group5.save_it_backend.utils.ValidationUtil;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductCategoryService productCategoryService;

    private final VendorService vendorService;

    private final ValidationUtil validationUtil;

    private final ImageService imageService;

    private final ApplicationContext applicationContext;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductResponse create(NewProductRequest productRequest) {
        validationUtil.validate(productRequest);
        if (productRequest.getImage().isEmpty()){
            throw new ConstraintViolationException("image is required", null);
        }

        Image image = imageService.saveToCloud(productRequest.getImage(), DirectoryImage.VENDOR_PRODUCT);
        ProductCategory productCategory = productCategoryService.getOrSave(productRequest.getCategory());

        Product newProduct = Product.builder()
                .productName(productRequest.getName())
                .productCategory(productCategory)
                .productDescription(productRequest.getDescription())
                .productImage(image)
                .build();
        productRepository.saveAndFlush(newProduct);
        return parseProductToProductResponse(newProduct);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<NewProductResponse> createBulk(List<ProductVendorRequest> productVendorRequests) {

        List<VendorProduct> vendorProducts = productVendorRequests.stream().map(
                product -> {

                    Image image = imageService.saveToCloud(product.getProductImage(), DirectoryImage.VENDOR_PRODUCT);

                    Product newProduct = productRepository.saveAndFlush(Product.builder()
                            .productName(product.getProductName())
                            .productDescription(product.getProductDescription())
                            .productCategory(productCategoryService.getById(product.getProductCategoryId()))
                            .productImage(image)
                            .build());

                    Vendor vendor = vendorService.getById(product.getVendorId());
                    return VendorProduct.builder()
                            .vendor(vendor)
                            .product(newProduct)
                            .price(product.getPrice())
                            .stocks(product.getStocks())
                            .build();
                }
        ).toList();

        getVendorProductService().createBulk(vendorProducts);

        List<NewProductResponse> newProductResponses = vendorProducts.stream().map(
                vendorProduct -> {
                    return NewProductResponse.builder()
                            .id(vendorProduct.getProduct().getId())
                            .name(vendorProduct.getProduct().getProductName())
                            .vendorName(vendorProduct.getVendor().getVendorName())
                            .category(vendorProduct.getProduct().getProductCategory().getCategoryName())
                            .description(vendorProduct.getProduct().getProductDescription())
                            .image(ImageResponse.builder()
                                    .name(vendorProduct.getProduct().getProductImage().getName())
                                    .url(vendorProduct.getProduct().getProductImage().getPath())
                                    .build())
                            .price(vendorProduct.getPrice())
                            .stocks(vendorProduct.getStocks())
                            .build();
                }
        ).toList();

       return newProductResponses;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createBulkInit(List<NewProductRequest> productRequests) {
        List<Product> products = productRequests.stream().map(
                request -> Product.builder()
                        .productName(request.getName())
                        .productCategory(productCategoryService.getOrSave(request.getCategory()))
                        .productDescription(request.getDescription())
                        .productImage(imageService.createInit(request.getImage()))
                        .build()
        ).toList();

        productRepository.saveAllAndFlush(products);
    }

    @Transactional(readOnly = true)
    @Override
    public Product getById(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
        }
        return optionalProduct.get();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Product> getAll(SearchProductRequest productRequest) {
        if (productRequest.getPage() <= 0){
            productRequest.setPage(1);
        }
        String validSortBy;
        if ("name".equalsIgnoreCase(productRequest.getSortBy()) || "category".equalsIgnoreCase(productRequest.getSortBy())){
            validSortBy = productRequest.getSortBy();
        }else {
            validSortBy = "name";
        }

        Sort sort = Sort.by(Sort.Direction.fromString(productRequest.getDirection()), /*productRequest.getSortBy()*/ validSortBy);

        Pageable pageable = PageRequest.of((productRequest.getPage() -1), productRequest.getSize(), sort); // rumus pagination

        Specification<Product> specification = ProductSpecification.getSpecification(productRequest);

        return productRepository.findAll(specification,pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Product update(Product product) {
        getById(product.getId());
        return productRepository.saveAndFlush(product);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        Product currentProduct = getById(id);
        productRepository.delete(currentProduct);
    }

    @Transactional(readOnly = true)
    @Override
    public Product findByName(String name) {
        return productRepository.findByProductName(name)
                .orElseThrow(() -> new RuntimeException("Product not found with name: " + name));
    }

    @Transactional(readOnly = true)
    @Override
    public Long count() {
        return productRepository.count();
    }

    private ProductResponse parseProductToProductResponse(Product product){

        String imageId;
        String name;
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        if(product.getProductImage() == null){
            imageId = null;
            name = null;
        } else {
            imageId = product.getProductImage().getId();
            name = product.getProductImage().getName();
        }

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getProductName())
                .category(product.getProductCategory().getCategoryName())
                .image(ImageResponse.builder()
                        .url(baseUrl + APIUrl.PRODUCT_IMAGE_API + imageId)
                        .name(name)
                        .build())
                .build();
    }

    private VendorProductService getVendorProductService(){
        return applicationContext.getBean(VendorProductService.class);
    }
}
