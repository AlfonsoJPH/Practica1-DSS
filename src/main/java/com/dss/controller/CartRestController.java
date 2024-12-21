package com.dss.controller;

import com.dss.model.Product;
import com.dss.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
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
		System.out.println(gson.toJson(cartProducts));
        return "redirect:/products"; // O la URL a la que deseas redirigir
    }

    @PostMapping("/api/cart/add/{id}")
    public String addToCartAPI(@PathVariable("id") Long productId, @CookieValue(value = CartService.CART_COOKIE_NAME, required = false) String cartCookie, HttpServletResponse response) {
        // Tu lógica para añadir el producto al carrito
        cartService.addProductToCart(productId, cartCookie, response);
        return "redirect:/products"; // O la URL a la que deseas redirigir
    }
    @PostMapping("/api/cart/remove/{productId}")
    public String removeFromCartAPI(@PathVariable Long productId, @CookieValue(value = CartService.CART_COOKIE_NAME, required = false) String cartCookie, HttpServletResponse response) {
        cartService.removeProductFromCart(productId, cartCookie, response);
        return "redirect:/cart";
    }

    @GetMapping("/api/cart/checkout")
    public ResponseEntity<ByteArrayResource> downloadTicketAPI(@CookieValue(value = CartService.CART_COOKIE_NAME, required = false) String cartCookie, HttpServletResponse response) {
        // Generar el ticket como ByteArrayOutputStream
        ByteArrayOutputStream pdfData = cartService.generateTicket(cartCookie);

        // Convertir el ByteArrayOutputStream a ByteArrayResource
        ByteArrayResource resource = new ByteArrayResource(pdfData.toByteArray());

        cartService.removeAllProductsFromCart(response);
        // Configurar la respuesta para descargar el archivo PDF
        return ResponseEntity.ok()
                .contentLength(pdfData.size())  // Usar size() en lugar de length()
                .header("Content-Disposition", "attachment; filename=ticket.pdf")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
