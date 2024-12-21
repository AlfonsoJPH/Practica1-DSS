package com.dss.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dss.service.ProductService;

import lombok.NoArgsConstructor;

import com.dss.model.Product;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@NoArgsConstructor
public class ProductRestController {

    @Autowired
    private ProductService productService;


    @GetMapping("/api/products")
    @ResponseBody
    public List<Product> getAllProductsAPI(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
  //       GsonBuilder builder = new GsonBuilder();
		// Gson gson = builder.create();
        System.out.println("Peticionn");
        return products;
    }

    @GetMapping("/api/products-by-id")
    @ResponseBody
    public List<Product> getProductsByIdAPI(@RequestParam String ids, Model model) {
        List<Integer> idList = Arrays.stream(ids.split("_"))
                                     .map(Integer::parseInt)
                                     .collect(Collectors.toList());
        List<Product> products = new ArrayList<>();
        for (int id : idList) {
            Product product = productService.getProductById((long) id);
            if (product != null && !products.contains(product)) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        System.out.println("Peticion ID");
        return products;
    }

    @PostMapping("/api/products/add")
    @ResponseBody
    public Integer Product(@RequestParam String name, @RequestParam String price) {
        double priceP = Double.parseDouble(price);
        Product product = new Product(name, priceP);
        productService.saveProduct(product);
        System.out.println("Peticion add");

        return 0;
        // return "redirect:/products";
    }

    @PostMapping("/api/products/edit/{id}")
    @ResponseBody
    public Integer editProduct(@PathVariable Long id, @RequestParam String name, @RequestParam double price) {
        Product product = productService.getProductById(id);
        System.out.println(name);
        if (product != null) {
            product.setName(name);
            product.setPrice(price);
            productService.saveProduct(product);
            return 0;
        }
        return 1;
        // return "redirect:/products";
    }

    @PostMapping("/api/products/delete")
    @ResponseBody
    public Integer deleteProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            productService.deleteProduct(id);
            return 0;
        }
        return 1;
        // return "redirect:/products";
    }
}
