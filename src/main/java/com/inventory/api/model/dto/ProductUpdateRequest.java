package com.inventory.api.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductUpdateRequest(
        @Size(min = 2, max = 100)
        String name,

        @Size(min = 1, max = 50)
        String sku,

        @Size(max = 255)
        String description,

        @DecimalMin("0.00")
        BigDecimal price,

        @PositiveOrZero
        Integer quantity,

        @PositiveOrZero
        Integer minQuantity,

        @Positive
        Long categoryId
) {}
