package com.mentesaudavel.mentesaudavel.core.services;

import com.mentesaudavel.mentesaudavel.core.dto.in.UserCreateRequestDTO;
import com.mentesaudavel.mentesaudavel.core.dto.out.UserCreateResponseDTO;
import com.mentesaudavel.mentesaudavel.core.entities.User;
import com.mentesaudavel.mentesaudavel.core.exceptions.UnprocessableEntityException;
import com.mentesaudavel.mentesaudavel.core.mappers.UserMapper;
import com.mentesaudavel.mentesaudavel.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;


    public UserCreateResponseDTO registerUser(UserCreateRequestDTO dto) {
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

        return UserMapper.entityToDTO(createdUser);
    }
}
