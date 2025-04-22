package com.mentesaudavel.mentesaudavel.core.services;

import com.mentesaudavel.mentesaudavel.core.dto.in.UserRegisterDTO;
import com.mentesaudavel.mentesaudavel.core.dto.out.UserRegisterResponseDTO;
import com.mentesaudavel.mentesaudavel.core.entities.User;
import com.mentesaudavel.mentesaudavel.core.exceptions.UnprocessableEntityException;
import com.mentesaudavel.mentesaudavel.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;


    public UserRegisterResponseDTO registerUser(UserRegisterDTO dto) {
        Optional<User> userExists = this.userRepository
                .findByEmail(dto.email().toLowerCase());

        if (userExists.isPresent()) {
            throw new UnprocessableEntityException("E-mail is already in use.");
        }

        User user = new User(
                dto.email(),
                dto.password()
        );

        var createdUser = this.userRepository.save(user);

        return this.redirectAfterRegistration(createdUser);
    }

    private UserRegisterResponseDTO redirectAfterRegistration(User user) {
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/psicologos/criar-perfil").build().toUri();

        return new UserRegisterResponseDTO(
                user.getId(),
                uri
        );
    }

}
