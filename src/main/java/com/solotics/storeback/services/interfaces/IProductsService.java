package com.solotics.storeback.services.interfaces;

import org.springframework.http.ResponseEntity;

import com.solotics.storeback.models.Product;

public interface IProductsService {

    ResponseEntity findAll();
    
    ResponseEntity save(Product product);

    ResponseEntity findById(Long id);
}
