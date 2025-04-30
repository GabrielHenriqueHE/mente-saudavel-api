package com.mentesaudavel.mentesaudavel.core.controllers;

import com.mentesaudavel.mentesaudavel.core.dto.in.PsychologistCreateRequestDTO;
import com.mentesaudavel.mentesaudavel.core.dto.out.AppResponse;
import com.mentesaudavel.mentesaudavel.core.dto.out.LinkResponseDTO;
import com.mentesaudavel.mentesaudavel.core.dto.out.PsychologistCreateResponseDTO;
import com.mentesaudavel.mentesaudavel.core.entities.User;
import com.mentesaudavel.mentesaudavel.core.helpers.LinkHelper;
import com.mentesaudavel.mentesaudavel.core.services.PsychologistService;
import com.mentesaudavel.mentesaudavel.core.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/psychologists")
public class PsychologistController {

    @Autowired
    private PsychologistService psychologistService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<AppResponse<PsychologistCreateResponseDTO>> createPsychologist(
            @RequestHeader("X-Access-Token") @NotNull String id,
            @RequestBody @Valid PsychologistCreateRequestDTO dto
    ) {
        UUID userId = UUID.fromString(id);
        User user = this.userService.findByUserId(userId);

        var serviceResponse = this.psychologistService.createPsychologist(user, dto);

        Map<String, LinkResponseDTO> links = new HashMap<>();
        links.put("index", LinkHelper.link("/", HttpMethod.GET));
        links.put("profile", LinkHelper.link("api/profile", HttpMethod.GET));

        AppResponse<PsychologistCreateResponseDTO> response = new AppResponse<>(
                HttpStatus.CREATED.value(),
                "Psychologist profile created successfully",
                serviceResponse,
                links
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
