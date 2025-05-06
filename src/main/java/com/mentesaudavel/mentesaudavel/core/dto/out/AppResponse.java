package com.mentesaudavel.mentesaudavel.core.dto.out;

import java.util.Map;

public record AppResponse<T>(
        Integer status,
        String message,
        T details,
        Map<String, LinkResponseDTO> links
) {
}
