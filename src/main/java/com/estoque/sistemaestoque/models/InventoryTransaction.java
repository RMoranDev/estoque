package com.estoque.sistemaestoque.models;

import java.time.LocalDateTime;

import com.estoque.sistemaestoque.enums.TransactionType;

public class InventoryTransaction {

    private Long id;
    private Product product;
    private TransactionType type;
    private Integer quantity;
    private LocalDateTime date;
    private User user;
}