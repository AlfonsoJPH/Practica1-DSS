package com.dss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dss.model.Product;
import com.dss.repo.ProductRepo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class DatabaseExportService {


    @Autowired
    private ProductRepo productRepo;

    public byte[] exportDatabaseToSql() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            String createTableScript = "CREATE TABLE IF NOT EXISTS products (id BIGINT PRIMARY KEY, name VARCHAR(255), price DECIMAL(10,2));\n";
            outputStream.write(createTableScript.getBytes());

            List<Product> products = productRepo.findAll();
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
