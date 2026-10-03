package com.estoque.sistemaestoque.repository;

import com.estoque.sistemaestoque.model.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
