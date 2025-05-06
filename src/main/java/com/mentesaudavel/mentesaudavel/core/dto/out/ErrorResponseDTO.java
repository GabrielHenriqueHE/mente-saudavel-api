package com.mentesaudavel.mentesaudavel.core.dto.out;

public record ErrorResponseDTO<T>(
        Integer status,
        String message,
        T details
) {
}
