package com.inventory.api.controller;

import com.inventory.api.model.dto.ProductCreateRequest;
import com.inventory.api.model.dto.ProductResponse;
import com.inventory.api.model.dto.ProductUpdateRequest;
import com.inventory.api.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductCreateRequest request) {
        ProductResponse productResponse = productService.create(request);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        ProductResponse productResponse = productService.findById(id);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        List<ProductResponse> productResponseList = productService.findAll();
        return ResponseEntity.ok(productResponseList);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequest request) {
        ProductResponse productResponse = productService.update(id, request);
        return ResponseEntity.ok(productResponse);
    }

}
