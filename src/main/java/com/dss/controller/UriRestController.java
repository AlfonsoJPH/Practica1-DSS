package com.dss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dss.service.ProductService;

import lombok.NoArgsConstructor;

import com.dss.model.Product;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@NoArgsConstructor
@RequestMapping(value = "/api")
public class UriRestController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Retorna el nombre de la plantilla sin la extensión
    }
}
