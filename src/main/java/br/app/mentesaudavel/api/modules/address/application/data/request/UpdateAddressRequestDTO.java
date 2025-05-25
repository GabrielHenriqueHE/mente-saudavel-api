package br.app.mentesaudavel.api.modules.address.application.data.request;

import jakarta.validation.constraints.*;

public record UpdateAddressRequestDTO(
        @NotBlank(message = "Description is required.")
        @Size(min = 5, max = 30, message = "Description length must be between 5 and 30.")
        String description,

        @NotBlank(message = "CEP is required.")
        @Size(min = 8, max = 8, message = "CEP must be exactly 8 digits.")
        @Pattern(regexp = "\\d{8}", message = "CEP must containt only numeric digits.")
        String cep,

        @NotBlank(message = "Street is required.")
        @Size(max = 100, message = "Street must be at most 100 characters.")
        String street,

        @Size(max = 50, message = "Complement must be at most 50 characters.")
        String complement,

        @NotBlank(message = "Neighborhood is required.")
        @Size(min = 5, max = 50, message = "Neighborhood must be at most 100 characters.")
        String neighborhood,

        @NotNull(message = "Number is required.")
        @Positive(message = "Number must be a positive value.")
        Integer number,

        @NotBlank(message = "City is required.")
        @Size(min = 3, max = 100, message = "City must be at most 100 characters.")
        String city,

        @NotBlank(message = "State is required.")
        @Size(min = 2, max = 2, message = "State must be exactly 2 characters long.")
        @Pattern(regexp = "[A-Z]{2}", message = "State must contain 2 uppercase letters")
        String state
) {}
