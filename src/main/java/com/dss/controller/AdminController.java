package com.dss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dss.model.Product;
import com.dss.service.DatabaseExportService;
import com.dss.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private final DatabaseExportService databaseExportService;
    @Autowired
    private ProductService productService;

    @Autowired
    public AdminController(DatabaseExportService databaseExportService) {
        this.databaseExportService = databaseExportService;
    }

    @GetMapping
    public String getAdminPage(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin"; // Nombre del archivo Thymeleaf en src/main/resources/templates/products.html
    }

    @GetMapping("/download-db-sql")
    public ResponseEntity<ByteArrayResource> downloadDatabase() {
        byte[] sqlData = databaseExportService.exportDatabaseToSql();
        ByteArrayResource resource = new ByteArrayResource(sqlData);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=database.sql");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(sqlData.length)
                .contentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }


    @GetMapping("/formulario-producto")
    public String showProductForm(@RequestParam(required = false) Long id, Model model) {
        if (id != null) {
            Product product = productService.getProductById(id);
            model.addAttribute("product", product); // Cargar el producto si se está editando
        }
        return "formulario-producto"; // Nombre del archivo Thymeleaf para el formulario
    }

    @PostMapping("/add")
    public String Product(@RequestParam String name, @RequestParam double price) {
        Product product = new Product(name, price);
        productService.saveProduct(product);

        return "redirect:/admin";
        // return "redirect:/products";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, @RequestParam String name, @RequestParam double price) {
        Product product = productService.getProductById(id);
        if (product != null) {
            product.setName(name);
            product.setPrice(price);
            productService.saveProduct(product);
        }
        return "redirect:/admin";
        // return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            productService.deleteProduct(id);
        }
        return "redirect:/admin";
        // return "redirect:/products";
    }
}
