package com.mentesaudavel.mentesaudavel.core.services;

import com.mentesaudavel.mentesaudavel.core.entities.User;
import com.mentesaudavel.mentesaudavel.core.exceptions.ResourceNotFoundException;
import com.mentesaudavel.mentesaudavel.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUserId(UUID id) {
        return this.userRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found from ID: "
                                .concat(String.valueOf(id))
                        )
                );
    }

    public User findUserByEmail(String email) {
        return this.userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found from email: "
                                .concat(email))
                );
    }
}
