package br.app.mentesaudavel.api.modules.authentication.application.services;

import br.app.mentesaudavel.api.modules.authentication.application.data.request.AuthenticateUserRequestDTO;
import br.app.mentesaudavel.api.modules.authentication.application.data.request.RegisterUserRequestDTO;
import br.app.mentesaudavel.api.modules.authentication.application.data.response.AuthenticationResponseDTO;
import br.app.mentesaudavel.api.modules.user.domain.model.User;
import br.app.mentesaudavel.api.modules.user.repositories.UserRepository;
import br.app.mentesaudavel.api.shared.exceptions.UnprocessableEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RegisterUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticateUserService authenticateUserService;

    public AuthenticationResponseDTO execute(RegisterUserRequestDTO request) {
        Map<String, String[]> errors = new HashMap<>();

        var userIsPresent = this.userRepository.existsByEmail(request.email());

        if (userIsPresent) {
            errors.put("email", new String[] {"Email is already in use."});
        }

        String encodedPassword = this.passwordEncoder.encode(request.password());

        if (!errors.isEmpty()) {
            throw new UnprocessableEntityException("Error during user registration.", errors);
        }

        this.userRepository.save(new User(request.email(), encodedPassword));

        return this.authenticateUserService.execute(new AuthenticateUserRequestDTO(request.email(), request.password()));
    }
}
