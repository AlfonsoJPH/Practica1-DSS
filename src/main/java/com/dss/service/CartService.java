package com.dss.service;

import com.dss.model.Product;
import com.dss.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private ProductRepo productRepo;

    public static final String CART_COOKIE_NAME = "cart";

    public List<Product> getProductsFromCart(String cartCookie) {
        List<Product> cartProducts = new ArrayList<>();

        if (cartCookie == null || cartCookie.isEmpty()) {
            return cartProducts;
        }
        String[] productIds = cartCookie.split("_");
        for (String id : productIds) {
            Product product = getProductById(Long.valueOf(id));
            if (product != null) {
                cartProducts.add(product);
            }
        }
        return cartProducts;
    }

    public void addProductToCart(Long productId, String cartCookie, HttpServletResponse response) {
        // Obtener productos actuales del carrito
        List<Long> productIds = new ArrayList<>();
        if (cartCookie != null && !cartCookie.isEmpty()) {
            String[] existingIds = cartCookie.split("_");
            for (String id : existingIds) {
                productIds.add(Long.valueOf(id));
            }
        }

        // Agregar el nuevo ID del producto
        productIds.add(productId);

        // Guardar el nuevo valor de la cookie
        String cookieValue = String.join("_", productIds.stream().map(String::valueOf).toArray(String[]::new));
        Cookie cookie = new Cookie(CART_COOKIE_NAME, cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 50); // 50 días
        response.addCookie(cookie);
    }


    public void removeProductFromCart(Long productId, String cartCookie, HttpServletResponse response) {
        // Obtener productos actuales del carrito
        List<Long> productIds = new ArrayList<>();
        if (cartCookie != null && !cartCookie.isEmpty()) {
            String[] existingIds = cartCookie.split("_");
            for (String id : existingIds) {
                productIds.add(Long.valueOf(id));
            }

            // Eliminar el ID del producto
            productIds.remove(productId);

            // Guardar el nuevo valor de la cookie
            String cookieValue = String.join("_", productIds.stream().map(String::valueOf).toArray(String[]::new));
            Cookie cookie = new Cookie(CART_COOKIE_NAME, cookieValue);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 50); // 50 días
            response.addCookie(cookie);
        }
    }

    //remove all products from cart
    public void removeAllProductsFromCart(HttpServletResponse response) {
        Cookie cookie = new Cookie(CART_COOKIE_NAME, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public Product getProductById(Long id) {
        return productRepo.findById(id).orElse(null);
    }

    public ByteArrayOutputStream generateTicket(String cartCookie) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();  // Usar ByteArrayOutputStream aquí
        try {
            // Obtener el nombre de usuario autenticado
            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            // Obtener productos del carrito (deberás implementar esta función)
            List<Product> products = getProductsFromCart(cartCookie);

            // Crear el documento PDF
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);  // Escribir en el ByteArrayOutputStream
            document.open();

            // Añadir contenido al documento PDF
            document.add(new Paragraph("Ticket de compra para " + username));
            document.add(new Paragraph("Productos:"));

            // Añadir cada producto y su precio
            for (Product product : products) {
                document.add(new Paragraph(product.getName() + " - " + product.getPrice()));
            }

            // Calcular el total
            double total = products.stream().mapToDouble(Product::getPrice).sum();
            document.add(new Paragraph("Total: " + total));

            // Cerrar el documento PDF
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        // Retornar el contenido del PDF como ByteArrayOutputStream
        return outputStream;
    }
}
