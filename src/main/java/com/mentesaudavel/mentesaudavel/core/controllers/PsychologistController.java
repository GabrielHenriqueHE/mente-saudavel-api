package com.mentesaudavel.mentesaudavel.core.controllers;

import com.mentesaudavel.mentesaudavel.core.dto.in.AddressListRequestDTO;
import com.mentesaudavel.mentesaudavel.core.dto.in.ContactListRequestDTO;
import com.mentesaudavel.mentesaudavel.core.dto.in.PsychologistCreateRequestDTO;
import com.mentesaudavel.mentesaudavel.core.dto.out.AppResponse;
import com.mentesaudavel.mentesaudavel.core.dto.out.LinkResponseDTO;
import com.mentesaudavel.mentesaudavel.core.dto.out.PsychologistCreateResponseDTO;
import com.mentesaudavel.mentesaudavel.core.helpers.LinkHelper;
import com.mentesaudavel.mentesaudavel.core.services.PsychologistService;
import com.mentesaudavel.mentesaudavel.core.services.PsychologistSpecialtyService;
import com.mentesaudavel.mentesaudavel.core.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/psychologists")
public class PsychologistController {

    @Autowired
    private PsychologistService psychologistService;

    @Autowired
    private UserService userService;

    @Autowired
    private PsychologistSpecialtyService psychologistSpecialtyService;

    @PostMapping
    public ResponseEntity<AppResponse<PsychologistCreateResponseDTO>> createPsychologist(
            @RequestBody @Valid PsychologistCreateRequestDTO dto
    ) {
        var serviceResponse = this.psychologistService.createPsychologist(dto);

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


    @PostMapping("/contacts")
    public ResponseEntity<AppResponse<Void>> addContactsToPsychologist(
            @RequestBody @Valid ContactListRequestDTO contactListRequest
    ) {
        this.psychologistService.addContactsToAuthenticatedPsychologist(contactListRequest.contacts());

        Map<String, LinkResponseDTO> links = new HashMap<>();
        links.put("profile", LinkHelper.link("/api/profile", HttpMethod.GET));

        AppResponse<Void> response = new AppResponse<>(
                HttpStatus.OK.value(),
                "Contacts added successfully",
                null,
                links
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/addresses")
    public ResponseEntity<AppResponse<Void>> addAddressesToPsychologist(
            @RequestBody @Valid AddressListRequestDTO addressListRequest
    ) {
        this.psychologistService.addAddressesToAuthenticatedPsychologist(addressListRequest.addresses());

        Map<String, LinkResponseDTO> links = new HashMap<>();
        links.put("profile", LinkHelper.link("/api/profile", HttpMethod.GET));

        AppResponse<Void> response = new AppResponse<>(
                HttpStatus.OK.value(),
                "Addresses added successfully",
                null,
                links
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/specialties/{specialtyId}")
    public ResponseEntity<AppResponse<Void>> linkPsychologistToSpecialty(
            @PathVariable Long specialtyId
    ) {
        this.psychologistSpecialtyService.linkPsychologistToSpecialty(specialtyId);

        AppResponse<Void> response = new AppResponse<>(
                HttpStatus.OK.value(),
                "Specialty successfully linked to psychologist profile.",
                null,
                null
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/specialties/{specialtyId}")
    public ResponseEntity<AppResponse<Void>> unlinkPsychologistFromSpecialty(
            @PathVariable Long specialtyId
    ) {
        this.psychologistSpecialtyService.unlinkPsychologistFromSpecialty(specialtyId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
