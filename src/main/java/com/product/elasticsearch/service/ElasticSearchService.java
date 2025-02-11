package com.product.elasticsearch.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.elasticsearch.entity.Product;
import com.product.elasticsearch.util.ElasticSearchUtil;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElasticSearchService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ElasticSearchService.class);

    private final ElasticsearchOperations elasticsearchOperations;
    private final ObjectMapper objectMapper;

    public ElasticSearchService(ElasticsearchOperations elasticsearchOperations, ObjectMapper objectMapper) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.objectMapper = objectMapper;
    }

    private List<Product> executeSearch(Query query) {
        SearchHits<Product> searchHits = elasticsearchOperations.search(query, Product.class);
        return searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    public List<Product> findProductsByName(String name) {
        return executeSearch(ElasticSearchUtil.buildNameQuery(name));
    }

    public List<Product> findProductsByPriceRange(double minPrice, double maxPrice) {
        return executeSearch(ElasticSearchUtil.buildPriceRangeQuery(minPrice, maxPrice));
    }

    public List<Product> findProductsByBrandAndCategory(String brand, String category) {
        return executeSearch(ElasticSearchUtil.buildBrandAndCategoryQuery(brand, category));
    }

    public List<Product> matchAllProducts() {
        return executeSearch(ElasticSearchUtil.buildMatchAllQuery());
    }

    public List<Product> searchProducts(String category, String brand, double minPrice, double maxPrice, String excludedDescription) {
        Query query = ElasticSearchUtil.buildSearchQuery(category, brand, minPrice, maxPrice, excludedDescription);

        log.info("Generated Query: {}", query);
        try {
            String queryJson = objectMapper.writeValueAsString(query);
            log.info("Generated Query JSON: {}", queryJson);
        } catch (Exception e) {
            log.error("Failed to serialize query", e);
        }

        return executeSearch(query);
    }
}
