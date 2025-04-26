package com.mentesaudavel.mentesaudavel.core.dto.out;

import com.mentesaudavel.mentesaudavel.core.enums.UserStatusEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserCreateResponseDTO(
        UUID id,
        String email,
        UserStatusEnum status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
