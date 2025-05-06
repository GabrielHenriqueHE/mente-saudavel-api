package com.mentesaudavel.mentesaudavel.core.dto.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record AddressListRequestDTO(
        @NotEmpty(message = "At least one address must be provided.")
        @Valid
        List<AddressCreateRequestDTO> addresses
) {
}
