package com.dss.service;

import com.dss.model.Product;
import com.dss.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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

    public Product getProductById(Long id) {
        return productRepo.findById(id).orElse(null);
    }
}
