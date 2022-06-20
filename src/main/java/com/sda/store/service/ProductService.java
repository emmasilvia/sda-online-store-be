package com.sda.store.service;

import com.sda.store.model.Product;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface ProductService {

    Product create(Product product);
    Product findById(Long id);
    Product update(Product product);
    Page<Product> searchProducts(Map<String, String> params);
    void delete(Long id);
}
