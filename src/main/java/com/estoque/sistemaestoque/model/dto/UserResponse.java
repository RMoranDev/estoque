package com.estoque.sistemaestoque.model.dto;

public record UserResponse(
        Long id,
        String name,
        String email,
        boolean active
) {}
