package com.inventory.api.service;

import com.inventory.api.model.entities.Category;
import com.inventory.api.model.entities.Product;
import com.inventory.api.repository.CategoryRepository;
import com.inventory.api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Product create(Product product, Long categoryId) {
        if (productRepository.existsBySku(product.getSku())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "SKU já cadastrado");
        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Product> search(String name) {
        return (name == null || name.isBlank())
                ? productRepository.findAll()
                : productRepository.findByNameContainingIgnoreCase(name);
    }
}
