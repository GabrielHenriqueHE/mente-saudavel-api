package com.mentesaudavel.mentesaudavel.core.dto.out;

public record AppResponse<T>(
        Integer status,
        String message,
        T details
) {
}
