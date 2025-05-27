package br.app.mentesaudavel.api.modules.psychologist.application.controllers;

import br.app.mentesaudavel.api.modules.psychologist.application.data.request.CreatePsychologistRequestDTO;
import br.app.mentesaudavel.api.modules.psychologist.application.data.response.GetPsychologistProfileResponseDTO;
import br.app.mentesaudavel.api.modules.psychologist.application.services.CreatePsychologistService;
import br.app.mentesaudavel.api.modules.psychologist.application.services.GetAllPsychologistsService;
import br.app.mentesaudavel.api.modules.security.helpers.AuthenticationHelper;
import br.app.mentesaudavel.api.modules.user.domain.model.User;
import br.app.mentesaudavel.api.shared.data.response.ApplicationResponseDTO;
import br.app.mentesaudavel.api.shared.data.response.SliceResponseDTO;
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
    private final GetAllPsychologistsService getAllPsychologistsService;

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

    @GetMapping
    public ResponseEntity<ApplicationResponseDTO<SliceResponseDTO<GetPsychologistProfileResponseDTO>>> getAllPsychologists(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        SliceResponseDTO<GetPsychologistProfileResponseDTO> serviceResponse = this.getAllPsychologistsService.execute(page, size);

        ApplicationResponseDTO<SliceResponseDTO<GetPsychologistProfileResponseDTO>> response = ApplicationResponseDTO
                .<SliceResponseDTO<GetPsychologistProfileResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("Psychologists list returned successfully")
                .details(serviceResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
