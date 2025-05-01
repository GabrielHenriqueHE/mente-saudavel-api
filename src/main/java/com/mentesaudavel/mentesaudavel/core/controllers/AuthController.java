package com.mentesaudavel.mentesaudavel.core.controllers;

import com.mentesaudavel.mentesaudavel.core.dto.in.AuthenticationRequestDTO;
import com.mentesaudavel.mentesaudavel.core.dto.in.UserCreateRequestDTO;
import com.mentesaudavel.mentesaudavel.core.dto.out.AppResponse;
import com.mentesaudavel.mentesaudavel.core.dto.out.LinkResponseDTO;
import com.mentesaudavel.mentesaudavel.core.dto.out.AuthenticationResponseDTO;
import com.mentesaudavel.mentesaudavel.core.helpers.LinkHelper;
import com.mentesaudavel.mentesaudavel.core.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AppResponse<AuthenticationResponseDTO>> registerUser(
            @RequestBody @Valid UserCreateRequestDTO dto
    ) {
        AuthenticationResponseDTO serviceResponse = this.authService.registerUser(dto);

        Map<String, LinkResponseDTO> links = new HashMap<>();
        links.put("psychologists", LinkHelper.link("/api/psychologists/create", HttpMethod.POST));

        AppResponse<AuthenticationResponseDTO> response = new AppResponse<>(
                HttpStatus.CREATED.value(),
                "User created successfully",
                serviceResponse,
                links
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AppResponse<AuthenticationResponseDTO>> login(
            @RequestBody @Valid AuthenticationRequestDTO dto
    ) {
        AuthenticationResponseDTO serviceResponse = this.authService.login(dto);

        Map<String, LinkResponseDTO> links = new HashMap<>();
        links.put("psychologists", LinkHelper.link("api/psychologists/create", HttpMethod.POST));

        AppResponse<AuthenticationResponseDTO> response = new AppResponse<>(
                HttpStatus.OK.value(),
                "User authenticated successfully",
                serviceResponse,
                links
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
