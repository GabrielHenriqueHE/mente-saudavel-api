package com.mentesaudavel.mentesaudavel.core.dto.out;

import java.time.LocalDateTime;
import java.util.UUID;

public record ContactGetResponseDTO(
        UUID id,
        String description,
        String email,
        String phoneNumber,
        String linkedin,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
