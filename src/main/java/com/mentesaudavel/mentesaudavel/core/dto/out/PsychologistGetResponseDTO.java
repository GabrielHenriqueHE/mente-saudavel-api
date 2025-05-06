package com.mentesaudavel.mentesaudavel.core.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record PsychologistGetResponseDTO(
    UUID id,
    String name,
    String crp,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate birthDate,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate activitiesStartDate,
    String about,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
