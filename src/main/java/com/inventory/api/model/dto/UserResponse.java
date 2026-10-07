package com.inventory.api.model.dto;

public record UserResponse(
        Long id,
        String name,
        String email,
        boolean active
) {}
