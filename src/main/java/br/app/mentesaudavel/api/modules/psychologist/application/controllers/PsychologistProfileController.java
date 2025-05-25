package br.app.mentesaudavel.api.modules.psychologist.application.controllers;

import br.app.mentesaudavel.api.modules.address.application.data.request.CreateAddressRequestDTO;
import br.app.mentesaudavel.api.modules.psychologist.application.data.request.UpdatePsychologistRequestDTO;
import br.app.mentesaudavel.api.modules.psychologist.application.services.CreatePsychologistAddressService;
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
@RequestMapping("api/psychologists/profile")
@RequiredArgsConstructor
public class PsychologistProfileController {

    private final CreatePsychologistAddressService createPsychologistAddressService;
    private final DeletePsychologistService deletePsychologistService;
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

    @DeleteMapping
    public ResponseEntity<ApplicationResponseDTO<Void>> deletePsychologist() {
        User user = AuthenticationHelper.getAuthenticatedUser();

        this.deletePsychologistService.execute(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/addresses")
    public ResponseEntity<ApplicationResponseDTO<Void>> createPsychologistAddress(
            @Valid @RequestBody CreateAddressRequestDTO data
    ) {
        User user = AuthenticationHelper.getAuthenticatedUser();
        this.createPsychologistAddressService.execute(user, data);

        ApplicationResponseDTO<Void> response = ApplicationResponseDTO
                .<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("Psychologist address created succesfully.")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
