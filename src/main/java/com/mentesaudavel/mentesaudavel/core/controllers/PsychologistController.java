package com.mentesaudavel.mentesaudavel.core.controllers;

import com.mentesaudavel.mentesaudavel.core.dto.in.PsychologistCreateDTO;
import com.mentesaudavel.mentesaudavel.core.dto.out.AppResponse;
import com.mentesaudavel.mentesaudavel.core.dto.out.PsychologistCreationResponseDTO;
import com.mentesaudavel.mentesaudavel.core.entities.User;
import com.mentesaudavel.mentesaudavel.core.services.PsychologistService;
import com.mentesaudavel.mentesaudavel.core.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/psychologists")
public class PsychologistController {

    @Autowired
    private PsychologistService psychologistService;

    @Autowired
    private UserService userService;

    @PostMapping("/create-profile")
    public ResponseEntity<AppResponse<PsychologistCreationResponseDTO>> createPsychologist(
            @RequestHeader("X-Access-Token") @NotNull String id,
            @RequestBody @Valid PsychologistCreateDTO dto
    ) {
        UUID userId = UUID.fromString(id);
        User user = this.userService.findByUserId(userId);

        var serviceResponse = this.psychologistService.createPsychologist(user, dto);

        AppResponse<PsychologistCreationResponseDTO> response = new AppResponse<>(
                HttpStatus.CREATED.value(),
                "Psychologist profile created successfully",
                serviceResponse
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
