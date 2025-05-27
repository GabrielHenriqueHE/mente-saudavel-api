package br.app.mentesaudavel.api.modules.authentication.application.controllers;

import br.app.mentesaudavel.api.modules.authentication.application.data.request.AuthenticateUserRequestDTO;
import br.app.mentesaudavel.api.modules.authentication.application.data.request.RegisterUserRequestDTO;
import br.app.mentesaudavel.api.modules.authentication.application.data.response.AuthenticationResponseDTO;
import br.app.mentesaudavel.api.modules.authentication.application.services.AuthenticateUserService;
import br.app.mentesaudavel.api.modules.authentication.application.services.RegisterUserService;
import br.app.mentesaudavel.api.shared.data.response.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final RegisterUserService registerUserService;
    private final AuthenticateUserService authenticateUserService;

    @PostMapping("/register")
    public ResponseEntity<ApplicationResponseDTO<AuthenticationResponseDTO>> register(
            @Valid @RequestBody RegisterUserRequestDTO request
    ) {
        AuthenticationResponseDTO useCaseResponse = this.registerUserService.execute(request);

        ApplicationResponseDTO<AuthenticationResponseDTO> response = ApplicationResponseDTO
                .<AuthenticationResponseDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message("User created successfully")
                .details(useCaseResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApplicationResponseDTO<AuthenticationResponseDTO>> authenticate(
            @Valid @RequestBody AuthenticateUserRequestDTO request
    ) {
        AuthenticationResponseDTO useCaseResponse = this.authenticateUserService.execute(request);

        ApplicationResponseDTO<AuthenticationResponseDTO> response = ApplicationResponseDTO
                .<AuthenticationResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("User authenticated successfully")
                .details(useCaseResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
