package com.mentesaudavel.mentesaudavel.core.dto.out;

import java.time.LocalDate;
import java.util.UUID;

public record PsychologistCreateResponseDTO(
        UUID id,
        String name,
        String crp,
        String about,
        LocalDate birthDate,
        LocalDate activitiesStartDate
) {
}
