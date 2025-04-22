package com.mentesaudavel.mentesaudavel.core.dto.out;

import java.time.LocalDate;
import java.util.UUID;

public record PsychologistCreationResponseDTO(
        UUID id,
        String name,
        String crp,
        LocalDate birthDate,
        LocalDate activitiesStartDate
) {
}
