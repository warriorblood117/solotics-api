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
import java.util.Optional;

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

    @Override
    public ResponseEntity<Map<String, Object>> save(Product product) {
        Map<String, Object> response = new HashMap<>();
        try {
            productsRepository.save(product);
            response.put("message", "Producto creado exitosamente.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("message", "Fallo al crear el producto.");
            response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> findById(Long id) {
        Optional<Product> product = this.productsRepository.findById(id);
        Map<String, Object> message = new HashMap<>();
        if (product.isPresent()) {
            message.put("message", product.get());
            return ResponseEntity.status(HttpStatus.FOUND).body(message);
        }
        message.put("message", "Producto no encontrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

}
