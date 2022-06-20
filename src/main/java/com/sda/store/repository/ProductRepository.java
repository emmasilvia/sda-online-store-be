package com.sda.store.repository;

import com.sda.store.model.Product;
import com.sda.store.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Product findByName(String name);
    Product findByPrice(Double price);
    Product findByProductType(ProductType productType);

}
