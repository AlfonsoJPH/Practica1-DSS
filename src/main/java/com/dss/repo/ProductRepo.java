package com.dss.repo;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dss.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {
    Product findByName(String name);
}
