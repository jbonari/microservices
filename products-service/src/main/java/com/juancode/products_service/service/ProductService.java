package com.juancode.products_service.service;

import com.juancode.products_service.model.dto.ProductRequest;
import com.juancode.products_service.model.dto.ProductResponse;
import com.juancode.products_service.model.entities.Product;
import com.juancode.products_service.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    public void addProduct(ProductRequest productRequest) {

//        Product product= new Product();
//        product.setSku(productRequest.getSku());
//        product.setName(productRequest.getName());
//        product.setDescription(productRequest.getDescription());
//        product.setPrice(productRequest.getPrice());
//        product.setStatus(productRequest.getStatus());

        Product product= Product.builder()
                .sku(productRequest.getSku())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .status(productRequest.getStatus())
                .build();

        productRepository.save(product);
        log.info("Product added: {}",product);
    }

    public List<ProductResponse> getAllProducts(){

        var products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .status(product.getStatus())
                .build();
    }




}
