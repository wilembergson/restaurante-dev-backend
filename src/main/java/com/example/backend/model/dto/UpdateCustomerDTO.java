package com.example.backend.model.dto;

public record UpdateCustomerDTO(
        Long phone,
        String email,
        String login,
        String password) {
}
