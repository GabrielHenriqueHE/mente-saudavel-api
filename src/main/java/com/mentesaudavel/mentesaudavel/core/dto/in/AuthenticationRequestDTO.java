package com.mentesaudavel.mentesaudavel.core.dto.in;

public record AuthenticationRequestDTO(
        String email,
        String password
) {
}
