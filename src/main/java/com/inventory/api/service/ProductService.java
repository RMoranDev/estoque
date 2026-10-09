package com.inventory.api.service;

import com.inventory.api.model.dto.ProductCreateRequest;
import com.inventory.api.model.dto.ProductResponse;
import com.inventory.api.model.dto.ProductUpdateRequest;
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
    public ProductResponse create(ProductCreateRequest request) {
        if (productRepository.existsBySku(request.sku())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "SKU já cadastrado");
        }

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));

        Product product = new Product();
        product.setName(request.name());
        product.setSku(request.sku());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        return toResponse(savedProduct);
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
        return toResponse(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public ProductResponse update(Long productId, ProductUpdateRequest request) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Produto não encontrado"));

        if (request.name() != null) {
            existingProduct.setName(request.name());
        }

        if (request.sku() != null) {
            if (productRepository.existsBySkuAndIdNot(request.sku(), productId)) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "SKU já cadastrado");
            }
            existingProduct.setSku(request.sku());
        }

        if (request.description() != null) {
            existingProduct.setDescription(request.description());
        }

        if (request.price() != null) {
            existingProduct.setPrice(request.price());
        }

        if (request.quantity() != null) {
            existingProduct.setQuantity(request.quantity());
        }

        if (request.minQuantity() != null) {
            existingProduct.setMinQuantity(request.minQuantity());
        }

        if (request.categoryId() != null) {
            Category category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Categoria não encontrada"));
            existingProduct.setCategory(category);
        }

        return toResponse(existingProduct);
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getSku(),
                product.getPrice(),
                product.getQuantity(),
                product.getMinQuantity(),
                product.getCategory().getId(),
                product.getCategory().getName()
        );
    }
}
