package com.mentesaudavel.mentesaudavel.core.dto.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PsychologistCreateDTO(
        @NotBlank(message = "Name is required.")
        @Size(max = 100, message = "Name must be at most 100 characters.")
        String name,

        @NotBlank(message = "CRP is required.")
        @Pattern(regexp = "^\\d{2}/\\d{5}$", message = "CRP must be in the format UF/XXXXX.")
        String crp,

        @NotNull(message = "Birth date is required.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate birthDate,

        @NotNull(message = "Activities start date is required.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate activitiesStartDate
) {
}
