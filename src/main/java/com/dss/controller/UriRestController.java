package com.dss.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dss.service.ProductService;
import java.util.Base64;
import java.util.HashMap;

import lombok.NoArgsConstructor;
import lombok.Data;

import com.dss.model.Product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@NoArgsConstructor
public class UriRestController {
    private final String expectedToken = Base64.getEncoder().encodeToString("admin:admin".getBytes(StandardCharsets.UTF_8));

    @GetMapping("/api/login")
    @ResponseBody
    public Map<String, String> login(@RequestParam String username, @RequestParam String password) {
        String token = username + ":" + password;
        String encodedToken = Base64.getEncoder().encodeToString(token.getBytes(StandardCharsets.UTF_8));
        System.out.println(encodedToken);
        Map<String, String> response = new HashMap<>();
        response.put("token", encodedToken);
        return response;
    }
    @GetMapping("/api/checkPrivileges")
    @ResponseBody
    public Boolean checkPrivileges  (@RequestParam String token) {
        System.out.println("Checking " + token + " == " + expectedToken + " ");
        return token.equals(expectedToken);
    }

}
