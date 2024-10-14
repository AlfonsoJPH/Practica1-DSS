package com.dss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dss.service.ProductService;

import lombok.NoArgsConstructor;

import com.dss.model.Product;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@NoArgsConstructor
@RequestMapping(value = "/")
public class UriController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products"; // Nombre del archivo Thymeleaf en src/main/resources/templates/products.html
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Retorna el nombre de la plantilla sin la extensión
    }

}
