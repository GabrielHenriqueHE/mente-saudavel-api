package com.mentesaudavel.mentesaudavel.core.dto.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PsychologistUpdateRequestDTO(
        @Size(max = 100, message = "Name must be at most 100 characters.")
        String name,

        @Pattern(regexp = "^\\d{2}/\\d{5}$", message = "CRP must be in the format UF/XXXXX.")
        String crp,

        @Size(max = 2600, message = "About text must be at most 2600 characters.")
        String about,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate birthDate,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate activitiesStartDate
) {
}
