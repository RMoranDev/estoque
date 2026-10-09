package com.inventory.api.model.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String sku,
        BigDecimal price,
        Integer quantity,
        Integer minQuantity,
        Long categoryId,
        String categoryName
) {}
