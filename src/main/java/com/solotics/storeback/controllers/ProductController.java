package com.solotics.storeback.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.solotics.storeback.models.Product;
import com.solotics.storeback.services.S3Service;
import com.solotics.storeback.services.impl.IProductServiceImpl;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private IProductServiceImpl iProductServiceImpl;

    @Autowired
    private S3Service s3Service;

    @GetMapping("/")
    public ResponseEntity findAll() {
        return this.iProductServiceImpl.findAll();
    }

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody Product product) {
        return this.iProductServiceImpl.save(product);
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("file") MultipartFile file,
            @RequestParam("productId") Long productId) {
        try {
            String bucketName = "curso-aws-tecnoparts";
            String folderName = "imagenes";
            String keyName = file.getOriginalFilename();

            // Subir archivo a S3
            s3Service.uploadFile(bucketName, folderName, keyName, file);

            // Construir la URL de la imagen
            String imageUrl = "https://" + bucketName + ".s3.amazonaws.com/" + folderName + "/" + keyName;

            // Buscar el producto por ID y actualizar la URL de la imagen
            ResponseEntity<Map<String, Object>> productResponse = iProductServiceImpl.findById(productId);
            if (productResponse.getStatusCode() == HttpStatus.FOUND) {
                Product product = (Product) productResponse.getBody().get("message");
                product.setImagen(imageUrl);
                iProductServiceImpl.save(product);
            }

            Map<String, Object> message = new HashMap<>();
            message.put("message", "Archivo subido correctamente a S3: " + keyName);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> error = new HashMap<>();
            error.put("message", "Error al subir archivo: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

}
