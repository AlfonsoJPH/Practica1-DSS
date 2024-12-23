package com.dss.controller;

import com.dss.model.Product;
import com.dss.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class CartRestController {

    @Autowired
    private CartService cartService;

    @GetMapping("/api/cart")
    public String viewCartAPI(@CookieValue(value = CartService.CART_COOKIE_NAME, required = false) String cartCookie, Model model) {
        List<Product> cartProducts = cartService.getProductsFromCart(cartCookie);
        model.addAttribute("cartProducts", cartProducts);
        GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
        return "redirect:/products";
    }

    @GetMapping("/api/cart/checkout")
    public ResponseEntity<ByteArrayResource> downloadTicketAPI(@RequestParam String ids,  HttpServletResponse response) {
        ByteArrayOutputStream pdfData = cartService.generateTicket(ids);

        ByteArrayResource resource = new ByteArrayResource(pdfData.toByteArray());

        return ResponseEntity.ok()
                .contentLength(pdfData.size()) 
                .header("Content-Disposition", "inline; filename=ticket.pdf")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
