package com.dss.controller;

import com.dss.model.Product;
import com.dss.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public String viewCart(@CookieValue(value = CartService.CART_COOKIE_NAME, required = false) String cartCookie, Model model) {
        List<Product> cartProducts = cartService.getProductsFromCart(cartCookie);
        model.addAttribute("cartProducts", cartProducts);
        return "cart"; // Devuelve el nombre de la plantilla
    }

    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable("id") Long productId, @CookieValue(value = CartService.CART_COOKIE_NAME, required = false) String cartCookie, HttpServletResponse response) {
        // Tu lógica para añadir el producto al carrito
        cartService.addProductToCart(productId, cartCookie, response);
        return "redirect:/products"; // O la URL a la que deseas redirigir
    }
    @PostMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId, @CookieValue(value = CartService.CART_COOKIE_NAME, required = false) String cartCookie, HttpServletResponse response) {
        cartService.removeProductFromCart(productId, cartCookie, response);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public ResponseEntity<ByteArrayResource> downloadTicket(@CookieValue(value = CartService.CART_COOKIE_NAME, required = false) String cartCookie, HttpServletResponse response) {
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
