package com.product.elasticsearch.service;

import com.product.elasticsearch.entity.Product;
import com.product.elasticsearch.repo.ProductRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import java.util.Optional;


@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product) {
        log.info("Saving product: {}", product);
        return productRepository.save(product);
    }

    public Optional<Product> getProductById(String id) {
        log.info("Fetching product with id: {}", id);
        return productRepository.findById(id);
    }

    public Page<Product> getAllProducts() {
        log.info("Fetching all products");
        Iterable<Product> products = productRepository.findAll();
        return (Page<Product>) products;
    }

    public void deleteProduct(String id) {
        log.info("Deleting product with id: {}", id);
        productRepository.deleteById(id);
    }

}
