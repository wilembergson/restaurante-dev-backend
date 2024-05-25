package com.example.backend.model.dto;

import lombok.Getter;

public record EmploeeyInfoDTO(
        String name,

        Long registration,

        Long phone,

        String email,

        String username,

        String role
) {
}
