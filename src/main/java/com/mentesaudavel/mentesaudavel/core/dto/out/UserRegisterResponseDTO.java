package com.mentesaudavel.mentesaudavel.core.dto.out;

import java.net.URI;
import java.util.UUID;

public record UserRegisterResponseDTO(
        UUID id,
        URI nextStep
) {
}
