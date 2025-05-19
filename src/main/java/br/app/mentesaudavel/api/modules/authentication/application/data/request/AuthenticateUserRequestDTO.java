package br.app.mentesaudavel.api.modules.authentication.application.data.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticateUserRequestDTO(
        @NotBlank(message = "Email is required.")
        @Email(message = "Email must be valid.")
        String email,

        @NotBlank(message = "Password is required.")
        String password
) {
}
