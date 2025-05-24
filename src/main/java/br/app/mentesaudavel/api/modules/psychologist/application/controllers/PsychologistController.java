package br.app.mentesaudavel.api.modules.psychologist.application.controllers;

import br.app.mentesaudavel.api.modules.psychologist.application.data.request.CreatePsychologistRequestDTO;
import br.app.mentesaudavel.api.modules.psychologist.application.services.CreatePsychologistService;
import br.app.mentesaudavel.api.modules.psychologist.application.services.DeletePsychologistService;
import br.app.mentesaudavel.api.modules.psychologist.application.services.UpdatePsychologistService;
import br.app.mentesaudavel.api.modules.security.helpers.AuthenticationHelper;
import br.app.mentesaudavel.api.modules.user.domain.model.User;
import br.app.mentesaudavel.api.shared.dto.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/psychologists")
@RequiredArgsConstructor
public class PsychologistController {

    private final CreatePsychologistService createPsychologistService;
    private final DeletePsychologistService deletePsychologistService;
    private final UpdatePsychologistService updatePsychologistService;

    @PostMapping
    public ResponseEntity<ApplicationResponseDTO<Void>> createPsychologist(
            @Valid @RequestBody CreatePsychologistRequestDTO data
    ) {
        User user = AuthenticationHelper.getAuthenticatedUser();
        this.createPsychologistService.execute(data, user);

        ApplicationResponseDTO<Void> response = ApplicationResponseDTO
                .<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("Psychologist profile created successfully.")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
