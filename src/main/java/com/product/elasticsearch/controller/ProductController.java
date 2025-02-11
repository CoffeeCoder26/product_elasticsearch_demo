package com.product.elasticsearch.controller;


import com.product.elasticsearch.entity.Product;
import com.product.elasticsearch.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/products")
class ProductController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        log.info("Received request to create product: {}", product);
        return productService.saveProduct(product);
    }

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable String id) {
        log.info("Received request to get product with id: {}", id);
        return productService.getProductById(id);
    }

    @GetMapping
    public Page<Product> getAllProducts() {
        log.info("Received request to get all products");
        return productService.getAllProducts();
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        log.info("Received request to delete product with id: {}", id);
        productService.deleteProduct(id);
    }

}
