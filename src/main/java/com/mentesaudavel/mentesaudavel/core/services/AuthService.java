package com.mentesaudavel.mentesaudavel.core.services;

import com.mentesaudavel.mentesaudavel.core.dto.in.AuthenticationRequestDTO;
import com.mentesaudavel.mentesaudavel.core.dto.in.UserCreateRequestDTO;
import com.mentesaudavel.mentesaudavel.core.dto.out.AuthenticationResponseDTO;
import com.mentesaudavel.mentesaudavel.core.entities.User;
import com.mentesaudavel.mentesaudavel.core.exceptions.UnprocessableEntityException;
import com.mentesaudavel.mentesaudavel.core.repositories.UserRepository;
import com.mentesaudavel.mentesaudavel.core.security.implementations.UserDetailsImpl;
import com.mentesaudavel.mentesaudavel.core.security.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;


    public AuthenticationResponseDTO registerUser(UserCreateRequestDTO dto) {
        Optional<User> userExists = this.userRepository
                .findByEmail(dto.email().toLowerCase());

        if (userExists.isPresent()) {
            throw new UnprocessableEntityException("E-mail is already in use.");
        }

        String hashedPassword = passwordEncoder.encode(dto.password());

        User user = new User(
                dto.email(),
                hashedPassword
        );

        this.userRepository.save(user);

        return this.login(new AuthenticationRequestDTO(dto.email(), dto.password()));
    }

    public AuthenticationResponseDTO login(AuthenticationRequestDTO dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                dto.email().toLowerCase(),
                dto.password()
        );
        var auth = this.authenticationManager.authenticate(usernamePassword);

        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

        return new AuthenticationResponseDTO(
                this.tokenService.generateAccessToken(userDetails.getUser())
        );
    }
}
