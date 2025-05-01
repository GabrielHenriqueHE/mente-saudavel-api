package com.mentesaudavel.mentesaudavel.core.security.implementations;

import com.mentesaudavel.mentesaudavel.core.entities.User;
import com.mentesaudavel.mentesaudavel.core.exceptions.ResourceNotFoundException;
import com.mentesaudavel.mentesaudavel.core.exceptions.UnauthorizedAccessException;
import com.mentesaudavel.mentesaudavel.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            User user = this.userService.findUserByEmail(username);

            return new UserDetailsImpl(user);
        } catch (ResourceNotFoundException ex) {
            throw new UnauthorizedAccessException(ex.getMessage());
        }
    }

    public UserDetails loadUserById(UUID id) {
        try {
            User user = this.userService.findByUserId(id);

            return new UserDetailsImpl(user);
        } catch (ResourceNotFoundException ex) {
            throw new UnauthorizedAccessException(ex.getMessage());
        }
    }
}
