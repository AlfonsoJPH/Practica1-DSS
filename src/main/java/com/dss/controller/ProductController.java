package com.dss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dss.service.ProductService;

import lombok.NoArgsConstructor;

import com.dss.model.Product;

import org.springframework.web.bind.annotation.GetMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@NoArgsConstructor
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/api/products")
    @ResponseBody
    public List<Product> getAllProductsAPI(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
  //       GsonBuilder builder = new GsonBuilder();
		// Gson gson = builder.create();
        return products;
    }
    @GetMapping("/products")
    public String getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products"; // Nombre del archivo Thymeleaf en src/main/resources/templates/products.html
    }
}
