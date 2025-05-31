package br.app.mentesaudavel.api.modules.psychologist.application.controllers;

import br.app.mentesaudavel.api.modules.address.application.data.request.CreateAddressRequestDTO;
import br.app.mentesaudavel.api.modules.contact.application.data.request.CreateContactRequestDTO;
import br.app.mentesaudavel.api.modules.psychologist.application.data.request.UpdatePsychologistRequestDTO;
import br.app.mentesaudavel.api.modules.psychologist.application.data.response.GetPsychologistProfileResponseDTO;
import br.app.mentesaudavel.api.modules.psychologist.application.services.*;
import br.app.mentesaudavel.api.modules.security.helpers.AuthenticationHelper;
import br.app.mentesaudavel.api.modules.specialization.application.data.request.LinkSpecializationToPsychologistRequestDTO;
import br.app.mentesaudavel.api.modules.specialization.application.data.response.LinkSpecializationToPsychologistResponseDTO;
import br.app.mentesaudavel.api.modules.specialization.application.services.LinkSpecializationToPsychologistService;
import br.app.mentesaudavel.api.modules.user.domain.model.User;
import br.app.mentesaudavel.api.shared.data.response.ApplicationResponseDTO;
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
    private final CreatePsychologistContactService createPsychologistContactService;
    private final DeletePsychologistService deletePsychologistService;
    private final GetPsychologistProfileService getPsychologistProfileService;
    private final LinkSpecializationToPsychologistService linkSpecializationToPsychologistService;
    private final UpdatePsychologistService updatePsychologistService;

    @GetMapping
    public ResponseEntity<ApplicationResponseDTO<GetPsychologistProfileResponseDTO>> getPsychologistProfile() {
        User user = AuthenticationHelper.getAuthenticatedUser();

        GetPsychologistProfileResponseDTO serviceResponse = this.getPsychologistProfileService.execute(user);

        ApplicationResponseDTO<GetPsychologistProfileResponseDTO> response = ApplicationResponseDTO
                .<GetPsychologistProfileResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Psychologist profile located successfully")
                .details(serviceResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

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

    @PostMapping("/contacts")
    public ResponseEntity<ApplicationResponseDTO<Void>> createPsychologistContact(
            @Valid @RequestBody CreateContactRequestDTO data
    ) {
        User user = AuthenticationHelper.getAuthenticatedUser();
        this.createPsychologistContactService.execute(user, data);

        ApplicationResponseDTO<Void> response = ApplicationResponseDTO
                .<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("Psychologist contact created successfully.")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/specializations")
    public ResponseEntity<ApplicationResponseDTO<LinkSpecializationToPsychologistResponseDTO>> linkSpecializationToPsychologist(
            @Valid @RequestBody LinkSpecializationToPsychologistRequestDTO data
    ) {
        User user = AuthenticationHelper.getAuthenticatedUser();

        LinkSpecializationToPsychologistResponseDTO serviceResponse = this.linkSpecializationToPsychologistService.execute(user, data);

        ApplicationResponseDTO<LinkSpecializationToPsychologistResponseDTO> response = ApplicationResponseDTO
                .<LinkSpecializationToPsychologistResponseDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message("Specializations linked successfully.")
                .details(serviceResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
