package com.mentesaudavel.mentesaudavel.core.dto.out;

import java.time.LocalDateTime;
import java.util.UUID;

public record AddressGetResponseDTO(
        UUID id,
        String description,
        String cep,
        String street,
        String complement,
        String neighborhood,
        Short number,
        String city,
        String state,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
