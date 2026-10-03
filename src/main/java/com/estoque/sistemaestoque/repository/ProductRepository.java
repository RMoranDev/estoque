package com.estoque.sistemaestoque.repository;

import com.estoque.sistemaestoque.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
    boolean existsBySku(String sku);
}
