package com.xoco.nuniez.yugabyte.controllers;

import javax.validation.Valid;

import com.xoco.nuniez.yugabyte.exceptions.ResourceNotFoundException;
import com.xoco.nuniez.yugabyte.models.Product;
import com.xoco.nuniez.yugabyte.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }


    @PostMapping
    public Product createProduct(@Valid @RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/{productId}")
    public Product updateProduct(@PathVariable Long productId, @Valid @RequestBody Product productRequest) {
        return productRepository.findById(productId)
                .map(product -> {
                    product.setProductName(productRequest.getProductName());
                    product.setDescription(productRequest.getDescription());
                    product.setPrice(productRequest.getPrice());
                    return productRepository.save(product);
                }).orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));
    }


    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        return productRepository.findById(productId)
                .map(product -> {
                    productRepository.delete(product);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));
    }
}
