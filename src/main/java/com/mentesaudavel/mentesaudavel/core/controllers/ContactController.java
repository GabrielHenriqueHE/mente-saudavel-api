package com.mentesaudavel.mentesaudavel.core.controllers;

import com.mentesaudavel.mentesaudavel.core.dto.in.ContactUpdateRequestDTO;
import com.mentesaudavel.mentesaudavel.core.dto.out.AppResponse;
import com.mentesaudavel.mentesaudavel.core.services.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;


    @PutMapping("/{id}")
    public ResponseEntity<AppResponse<Void>> updateContact(
            @PathVariable UUID id,
            @RequestBody @Valid ContactUpdateRequestDTO dto
    ) {
        this.contactService.updateContact(id, dto);

        AppResponse<Void> response = new AppResponse<>(
                HttpStatus.OK.value(),
                "Contact updated successfully",
                null,
                null
        );


        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(
            @PathVariable UUID id
    ) {
        this.contactService.deleteContact(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
