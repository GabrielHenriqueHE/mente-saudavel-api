package com.mentesaudavel.mentesaudavel.core.controllers;

import com.mentesaudavel.mentesaudavel.core.dto.in.UserRegisterDTO;
import com.mentesaudavel.mentesaudavel.core.dto.out.AppResponse;
import com.mentesaudavel.mentesaudavel.core.dto.out.UserRegisterResponseDTO;
import com.mentesaudavel.mentesaudavel.core.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    private ResponseEntity<AppResponse<UserRegisterResponseDTO>> registerUser(
            @RequestBody @Valid UserRegisterDTO dto
    ) {
        UserRegisterResponseDTO serviceResponse = this.authService.registerUser(dto);

        AppResponse<UserRegisterResponseDTO> response = new AppResponse<>(
                HttpStatus.CREATED.value(),
                "User created successfully",
                serviceResponse
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
