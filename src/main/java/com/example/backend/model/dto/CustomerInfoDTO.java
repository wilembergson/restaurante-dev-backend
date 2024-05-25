package com.example.backend.model.dto;

public record CustomerInfoDTO(
        String name,
        Long cpf,
        Long phone,
        String email,
        String login,
        String role
) {
}
