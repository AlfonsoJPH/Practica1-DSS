package com.dss.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import com.dss.model.Product;
import com.dss.repo.ProductRepo;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class ProductService  {

    @Autowired
    private ProductRepo productRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }
    public Product getProductById(Long id) {
        return productRepo.findById(id).orElse(null);
    }
    public void saveProduct(Product product) {
        productRepo.save(product);
    }
    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }


}
