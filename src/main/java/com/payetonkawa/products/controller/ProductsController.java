package com.payetonkawa.products.controller;

import com.payetonkawa.products.entity.ProductsEntity;
import com.payetonkawa.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<ProductsEntity> createProducts(@RequestBody ProductsEntity product) {
        ProductsEntity createdProduct = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping
    public ResponseEntity<List<ProductsEntity>> getProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductsEntity> getProductById(@PathVariable UUID id) {
        return productRepository.findById(id)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductsEntity> updateProduct(@PathVariable UUID id, @RequestBody ProductsEntity productDetails) {
        return productRepository.findById(id).map(product -> {
            product.setName(productDetails.getName());
            product.setPrice(productDetails.getPrice());
            product.setDescription(productDetails.getDescription());
            product.setColor(productDetails.getColor());
            product.setStock(productDetails.getStock());
            ProductsEntity updatedProduct = productRepository.save(product);
            return ResponseEntity.ok(updatedProduct);
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable UUID id) {
        return productRepository.findById(id).map(product -> {
            productRepository.delete(product);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
