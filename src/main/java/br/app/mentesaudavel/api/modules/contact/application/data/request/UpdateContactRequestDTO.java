package br.app.mentesaudavel.api.modules.contact.application.data.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateContactRequestDTO(
        @NotBlank(message = "Description is required.")
        @Size(max = 30, message = "Description must be at most 30 characters.")
        String description,

        @Pattern(
                regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
                message = "Email must be valid."
        )
        String email,

        @Pattern(regexp = "^\\d{0,20}$", message = "Phone number must contain only digits and be at most 20 characters.")
        String phoneNumber,

        @Pattern(
                regexp = "^(https?://)?(www\\.)?linkedin\\.com/in/[a-zA-Z0-9\\-_%]+/?$",
                message = "LinkedIn must be a valid LinkedIn profile URL."
        )
        String linkedin
) {}
