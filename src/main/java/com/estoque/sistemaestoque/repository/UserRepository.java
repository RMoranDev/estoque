package com.estoque.sistemaestoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserRepository, Long> {
    Optional<UserRepository> findByEmail(String email);
}
