package com.estoque.sistemaestoque.service;

import com.estoque.sistemaestoque.model.entities.Category;
import com.estoque.sistemaestoque.repository.CategoryRepository;
import com.estoque.sistemaestoque.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Category create(Category category) {
        if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nome já cadastrado");
        }

        return categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));
    }

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAllByOrderByNameAsc();
    }

    @Transactional
    public Category update(Long categoryId, Category category) {

        Category existingCategory = findById(categoryId);

        if (categoryRepository.existsByNameIgnoreCaseAndIdNot(category.getName(), categoryId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nome já cadastrado");
        }

        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());

        return categoryRepository.save(existingCategory);
    }

    @Transactional
    public void deleteById(Long categoryId) {
        findById(categoryId);
        if (productRepository.existsByCategoryId(categoryId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não é possível excluir uma categoria que possui produtos");
        }

        categoryRepository.deleteById(categoryId);
    }
}
