package com.inventory.api.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductCreateRequest(
    @NotBlank
    String name,

    @NotBlank
    String sku,

    String description,

    @NotNull
    @PositiveOrZero
    BigDecimal price,

    @NotNull
    @PositiveOrZero
    Integer quantity,

    @PositiveOrZero
    Integer minQuantity,

    @NotNull
    Long categoryId
) {}
