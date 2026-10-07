package com.inventory.api.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
        @Size(min = 2, max = 100)
        String name,

        @Email
        @Size(max = 150)
        String email,

        @Size(min = 8, max = 60)
        String password
) {}
