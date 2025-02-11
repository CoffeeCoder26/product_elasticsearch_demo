package com.product.elasticsearch.util;


import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;

public class ElasticSearchUtil {

    public static CriteriaQuery buildNameQuery(String name) {
        return new CriteriaQuery(new Criteria("name").is(name));
    }

    public static CriteriaQuery buildPriceRangeQuery(double minPrice, double maxPrice) {
        return new CriteriaQuery(new Criteria("price").between(minPrice, maxPrice));
    }

    public static CriteriaQuery buildBrandAndCategoryQuery(String brand, String category) {
        return new CriteriaQuery(new Criteria("brand").is(brand).and("category").is(category));
    }

    public static CriteriaQuery buildMatchAllQuery() {
        return new CriteriaQuery(new Criteria());
    }

    public static CriteriaQuery buildSearchQuery(String category, String brand, double minPrice, double maxPrice, String excludedDescription) {
        Criteria criteria = new Criteria()
                .and("category").is(category)
                .or("brand").is(brand)
                .and("price").between(minPrice, maxPrice)
                .not().and("description").contains(excludedDescription);
        return new CriteriaQuery(criteria);
    }
}

