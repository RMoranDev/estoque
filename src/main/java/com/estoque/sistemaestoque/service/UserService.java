package com.estoque.sistemaestoque.service;

import com.estoque.sistemaestoque.model.dto.UserCreateRequest;
import com.estoque.sistemaestoque.model.dto.UserResponse;
import com.estoque.sistemaestoque.model.dto.UserUpdateRequest;
import com.estoque.sistemaestoque.model.entities.User;
import com.estoque.sistemaestoque.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse create(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado");
        }
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        User savedUser = userRepository.save(user);

        return toResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public UserResponse findById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        return toResponse(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public UserResponse update(Long userId, UserUpdateRequest request) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (request.name() != null) {
            existingUser.setName(request.name());
        }

        if (request.email() != null) {
            if (userRepository.existsByEmailAndIdNot(request.email(), userId)) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "E-mail já cadastrado");
            }
            existingUser.setEmail(request.email());
        }

        if (request.password() != null) {
            existingUser.setPassword(passwordEncoder.encode(request.password()));
        }

        return toResponse(existingUser);
    }

    public void deactivateUser(Long userId) {}
    public void activateUser(Long userId) {}

    private UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.isActive());
    }
}
