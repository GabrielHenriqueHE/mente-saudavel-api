package com.mentesaudavel.mentesaudavel.core.dto.out;

import java.net.URI;

public record LinkResponseDTO(
        String method,
        URI path
) {
}
