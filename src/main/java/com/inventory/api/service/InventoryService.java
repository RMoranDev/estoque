package com.inventory.api.service;

import com.inventory.api.model.entities.InventoryTransaction;
import com.inventory.api.model.entities.Product;
import com.inventory.api.model.entities.User;
import com.inventory.api.model.enums.TransactionType;
import com.inventory.api.repository.InventoryTransactionRepository;
import com.inventory.api.repository.ProductRepository;
import com.inventory.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final InventoryTransactionRepository transactionRepository;

    @Transactional
    public InventoryTransaction register(Long productId, Long userId,
                                         TransactionType type, Integer quantity, String notes) {
        if (quantity == null || quantity <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantidade inválida");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        int newQuantity = switch (type) {
            case ENTRADA -> product.getQuantity() +  quantity;
            case SAIDA -> {
                if (product.getQuantity() < quantity) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Estoque insuficiente");
                }
                yield product.getQuantity() - quantity;
            }
        };
        product.setQuantity(newQuantity);

        InventoryTransaction transaction = new InventoryTransaction();
        transaction.setProduct(product);
        transaction.setUser(user);
        transaction.setType(type);
        transaction.setQuantity(quantity);
        transaction.setNotes(notes);
        return transactionRepository.save(transaction);
    }

    @Transactional(readOnly = true)
    public List<InventoryTransaction> history(Long productId) {
        return  transactionRepository.findByProductIdOrderByCreatedAtDesc(productId);
    }

}
