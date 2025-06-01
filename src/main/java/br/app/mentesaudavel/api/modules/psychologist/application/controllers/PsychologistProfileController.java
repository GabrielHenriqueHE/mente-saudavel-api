package br.app.mentesaudavel.api.modules.psychologist.application.controllers;

import br.app.mentesaudavel.api.modules.address.application.data.request.CreateAddressRequestDTO;
import br.app.mentesaudavel.api.modules.address.application.data.response.GetAddressResponseDTO;
import br.app.mentesaudavel.api.modules.address.application.services.GetAuthenticatedPsychologistAddressesService;
import br.app.mentesaudavel.api.modules.contact.application.data.request.CreateContactRequestDTO;
import br.app.mentesaudavel.api.modules.contact.application.data.response.GetContactResponseDTO;
import br.app.mentesaudavel.api.modules.contact.application.services.GetAuthenticatedPsychologistContactsService;
import br.app.mentesaudavel.api.modules.psychologist.application.data.request.UpdatePsychologistRequestDTO;
import br.app.mentesaudavel.api.modules.psychologist.application.data.response.GetPsychologistProfileResponseDTO;
import br.app.mentesaudavel.api.modules.psychologist.application.services.*;
import br.app.mentesaudavel.api.modules.security.helpers.AuthenticationHelper;
import br.app.mentesaudavel.api.modules.specialization.application.data.request.LinkSpecializationToPsychologistRequestDTO;
import br.app.mentesaudavel.api.modules.specialization.application.data.response.LinkSpecializationToPsychologistResponseDTO;
import br.app.mentesaudavel.api.modules.specialization.application.services.LinkSpecializationToPsychologistService;
import br.app.mentesaudavel.api.modules.specialization.application.services.UnlinkSpecializationFromPsychologistService;
import br.app.mentesaudavel.api.modules.user.domain.model.User;
import br.app.mentesaudavel.api.shared.data.response.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/psychologists/profile")
@RequiredArgsConstructor
public class PsychologistProfileController {

    private final CreatePsychologistAddressService createPsychologistAddressService;
    private final CreatePsychologistContactService createPsychologistContactService;
    private final DeletePsychologistService deletePsychologistService;
    private final GetAuthenticatedPsychologistProfileService getAuthenticatedPsychologistProfileService;
    private final GetAuthenticatedPsychologistAddressesService getAuthenticatedPsychologistAddressesService;
    private final GetAuthenticatedPsychologistContactsService getAuthenticatedPsychologistContactsService;
    private final LinkSpecializationToPsychologistService linkSpecializationToPsychologistService;
    private final UnlinkSpecializationFromPsychologistService unlinkSpecializationFromPsychologistService;
    private final UpdatePsychologistService updatePsychologistService;

    @GetMapping
    public ResponseEntity<ApplicationResponseDTO<GetPsychologistProfileResponseDTO>> getAuthenticatedUserPsychologistProfile() {
        User user = AuthenticationHelper.getAuthenticatedUser();

        GetPsychologistProfileResponseDTO serviceResponse = this.getAuthenticatedPsychologistProfileService.execute(user);

        RepresentationModel<?> references = new RepresentationModel<>();
        references.add(linkTo(methodOn(this.getClass()).getAuthenticatedPsychologistAddresses()).withRel("addresses"));
        references.add(linkTo(methodOn(this.getClass()).getAuthenticatedPsychologistContacts()).withRel("contacts"));

        ApplicationResponseDTO<GetPsychologistProfileResponseDTO> response = ApplicationResponseDTO
                .<GetPsychologistProfileResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Psychologist profile located successfully")
                .details(serviceResponse)
                .links(references.getLinks().toList())
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

    @GetMapping("/addresses")
    public ResponseEntity<ApplicationResponseDTO<List<GetAddressResponseDTO>>> getAuthenticatedPsychologistAddresses() {
        User user = AuthenticationHelper.getAuthenticatedUser();
        List<GetAddressResponseDTO> serviceResponse = this.getAuthenticatedPsychologistAddressesService.execute(user);

        ApplicationResponseDTO<List<GetAddressResponseDTO>> response = ApplicationResponseDTO
                .<List<GetAddressResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("Psychologist addresses located successfully.")
                .details(serviceResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
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

    @GetMapping("/contacts")
    public ResponseEntity<ApplicationResponseDTO<List<GetContactResponseDTO>>> getAuthenticatedPsychologistContacts() {
        User user = AuthenticationHelper.getAuthenticatedUser();
        List<GetContactResponseDTO> serviceResponse = this.getAuthenticatedPsychologistContactsService.execute(user);

        ApplicationResponseDTO<List<GetContactResponseDTO>> response = ApplicationResponseDTO
                .<List<GetContactResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("Psychologist contacts located succesfully.")
                .details(serviceResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
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

    @DeleteMapping("/specializations/{id}")
    public ResponseEntity<Void> unlinkSpecializationFromPsychologist(
            @PathVariable("id") Long id
    ) {
        User user = AuthenticationHelper.getAuthenticatedUser();

        this.unlinkSpecializationFromPsychologistService.execute(user, id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
