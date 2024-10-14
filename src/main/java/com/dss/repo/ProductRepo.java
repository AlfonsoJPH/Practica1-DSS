package com.dss.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dss.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Product findByName(String name);
}
