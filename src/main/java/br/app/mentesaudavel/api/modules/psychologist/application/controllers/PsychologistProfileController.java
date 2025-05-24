package br.app.mentesaudavel.api.modules.psychologist.application.controllers;

import br.app.mentesaudavel.api.modules.psychologist.application.data.request.UpdatePsychologistRequestDTO;
import br.app.mentesaudavel.api.modules.psychologist.application.services.UpdatePsychologistService;
import br.app.mentesaudavel.api.modules.security.helpers.AuthenticationHelper;
import br.app.mentesaudavel.api.modules.user.domain.model.User;
import br.app.mentesaudavel.api.shared.dto.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/psychologists/profile")
@RequiredArgsConstructor
public class PsychologistProfileController {

    private final UpdatePsychologistService updatePsychologistService;

    @PutMapping
    public ResponseEntity<ApplicationResponseDTO<Void>> updatePsychologist(
            @Valid @RequestBody UpdatePsychologistRequestDTO data
    ) {
        User user = AuthenticationHelper.getAuthenticatedUser();
        this.updatePsychologistService.execute(user, data);

        ApplicationResponseDTO<Void> response = ApplicationResponseDTO
                .<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Psychologist profile updated successfully")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
