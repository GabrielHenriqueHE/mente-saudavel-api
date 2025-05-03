package com.mentesaudavel.mentesaudavel.core.dto.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ContactListRequestDTO(
        @NotEmpty(message = "At least one contact must be provided.")
        @Valid
        List<ContactCreateRequestDTO> contacts
) {
}
