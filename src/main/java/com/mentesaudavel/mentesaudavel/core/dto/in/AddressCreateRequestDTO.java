package com.mentesaudavel.mentesaudavel.core.dto.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record AddressCreateRequestDTO(
        @NotBlank(message = "Description is required")
        @Size(max = 30, message = "Description must be at most 30 characters long")
        String description,

        @NotBlank(message = "CEP is required")
        @Size(min = 8, max = 8, message = "CEP must be exactly 8 digits")
        @Pattern(regexp = "\\d{8}", message = "CEP must contain only numeric digits")
        String cep,

        @NotBlank(message = "Street is required")
        @Size(max = 100, message = "Street must be at most 100 characters long")
        String street,

        @Size(max = 100, message = "Complement must be at most 100 characters long")
        String complement,

        @NotBlank(message = "Neighborhood is required")
        @Size(max = 100, message = "Neighborhood must be at most 100 characters long")
        String neighborhood,

        @NotNull(message = "Number is required")
        @Positive(message = "Number must be a positive value")
        Short number,

        @NotBlank(message = "City is required")
        @Size(max = 100, message = "City must be at most 100 characters long")
        String city,

        @NotBlank(message = "State is required")
        @Size(min = 2, max = 2, message = "State must be exactly 2 characters long")
        @Pattern(regexp = "[A-Z]{2}", message = "State must contain 2 uppercase letters")
        String state

) {}
