package br.app.mentesaudavel.api.modules.authentication.application.services;

import br.app.mentesaudavel.api.modules.authentication.application.data.request.AuthenticateUserRequestDTO;
import br.app.mentesaudavel.api.modules.authentication.application.data.response.AuthenticationResponseDTO;
import br.app.mentesaudavel.api.modules.authentication.domain.model.UserDetailsImpl;
import br.app.mentesaudavel.api.modules.security.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthenticateUserService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationResponseDTO execute(AuthenticateUserRequestDTO request) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                request.email().toLowerCase(),
                request.password()
        );

        Authentication authentication = this.authenticationManager.authenticate(usernamePassword);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String token = this.jwtService.generate(userDetails.getUser());
        Instant expiresAt = jwtService.getExpirationDate(token);
        Long expiresIn = Duration.between(Instant.now(), expiresAt).getSeconds();

        return AuthenticationResponseDTO
                .builder()
                .accessToken(token)
                .expiresIn(expiresIn)
                .build();
    }
}
