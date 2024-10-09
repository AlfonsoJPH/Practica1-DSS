package com.dss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dss.service.ProductService;
import com.dss.model.Product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/add")
    public String addProduct(@RequestParam String name, @RequestParam double price) {
        Product product = new Product(name, price);
        productService.saveProduct(product);

        return "redirect:/admin";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@RequestParam Long id, @RequestParam String name, @RequestParam double price) {
        Product product = productService.getProductById(id);
        if (product != null) {
            product.setName(name);
            product.setPrice(price);
            productService.saveProduct(product);
        }
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@RequestParam Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            productService.deleteProduct(id);
        }
        return "redirect:/admin";
    }


}
