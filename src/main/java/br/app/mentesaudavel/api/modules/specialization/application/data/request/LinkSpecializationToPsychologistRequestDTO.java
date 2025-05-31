package br.app.mentesaudavel.api.modules.specialization.application.data.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record LinkSpecializationToPsychologistRequestDTO(
        @NotNull(message = "Specializations list is required.")
        @NotEmpty(message = "At least one specialization must be provided.")
        List<Long> specializations
) {}
