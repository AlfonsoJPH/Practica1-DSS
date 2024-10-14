package com.dss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.dss.model.Product;
import com.dss.repo.ProductRepo;

@Service
public class DatabaseExportService {

    @Autowired
    private ProductRepo productRepo;

    public byte[] exportDatabaseToSql() {
        List<Product> products = productRepo.findAll();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            for (Product product : products) {
                String sql = String.format(
                        "INSERT INTO products (id, name, price) VALUES (%d, '%s', %.2f);\n",
                        product.getId(), product.getName(), product.getPrice());
                outputStream.write(sql.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }
}
