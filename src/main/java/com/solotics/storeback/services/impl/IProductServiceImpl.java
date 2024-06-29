package com.solotics.storeback.services.impl;

import com.solotics.storeback.models.Product;
import com.solotics.storeback.repositories.ProductsRepository;
import com.solotics.storeback.services.interfaces.IProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IProductServiceImpl implements IProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public ResponseEntity<?> findAll() {
        try {
            List<Product> products = productsRepository.findAll();

            if (products.isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "No se encontraron productos");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("message", products);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error al obtener los productos");
            errorResponse.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
