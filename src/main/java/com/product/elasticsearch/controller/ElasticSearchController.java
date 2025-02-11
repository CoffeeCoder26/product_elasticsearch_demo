package com.product.elasticsearch.controller;

import com.product.elasticsearch.entity.Product;
import com.product.elasticsearch.service.ElasticSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/elasticsearch")
public class ElasticSearchController {

    private final ElasticSearchService elasticSearchService;

    public ElasticSearchController(ElasticSearchService elasticSearchService) {
        this.elasticSearchService = elasticSearchService;
    }

    @GetMapping("/search/name")
    public List<Product> findProductsByName(@RequestParam String name) {
        return elasticSearchService.findProductsByName(name);
    }

    @GetMapping("/search/price-range")
    public List<Product> findProductsByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        return elasticSearchService.findProductsByPriceRange(minPrice, maxPrice);
    }

    @GetMapping("/search/brand-category")
    public List<Product> findProductsByBrandAndCategory(@RequestParam String brand, @RequestParam String category) {
        return elasticSearchService.findProductsByBrandAndCategory(brand, category);
    }

    @GetMapping("/match-all")
    public List<Product> matchAllProducts() {
        return elasticSearchService.matchAllProducts();
    }

    @GetMapping("/search")
    public List<Product> searchProducts(
            @RequestParam String category,
            @RequestParam String brand,
            @RequestParam double minPrice,
            @RequestParam double maxPrice,
            @RequestParam String excludedDescription
    ) {
        return elasticSearchService.searchProducts(category, brand, minPrice, maxPrice, excludedDescription);
    }
}
