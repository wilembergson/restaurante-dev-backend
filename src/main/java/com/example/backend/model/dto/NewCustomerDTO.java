package com.example.backend.model.dto;

import lombok.Getter;

public record NewCustomerDTO(
        String name,
        Long cpf,
        Long phone,
        String email,
        String login,
        String password) {
}
