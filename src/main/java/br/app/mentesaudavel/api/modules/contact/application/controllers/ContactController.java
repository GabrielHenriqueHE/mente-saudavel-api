package br.app.mentesaudavel.api.modules.contact.application.controllers;

import br.app.mentesaudavel.api.modules.contact.application.data.request.UpdateContactRequestDTO;
import br.app.mentesaudavel.api.modules.contact.application.services.DeleteContactService;
import br.app.mentesaudavel.api.modules.contact.application.services.UpdateContactService;
import br.app.mentesaudavel.api.modules.security.helpers.AuthenticationHelper;
import br.app.mentesaudavel.api.modules.user.domain.model.User;
import br.app.mentesaudavel.api.shared.data.response.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final DeleteContactService deleteContactService;
    private final UpdateContactService updateContactService;

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponseDTO<Void>> updateContact(
            @PathVariable("id") String id,
            @Valid @RequestBody UpdateContactRequestDTO data
    ) {
        User user = AuthenticationHelper.getAuthenticatedUser();

        this.updateContactService.execute(user, UUID.fromString(id), data);

        ApplicationResponseDTO<Void> response = ApplicationResponseDTO
                .<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Contact updated successfully.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(
            @PathVariable("id") String id
    ) {
        User user = AuthenticationHelper.getAuthenticatedUser();

        this.deleteContactService.execute(user, UUID.fromString(id));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
