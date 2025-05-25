package br.app.mentesaudavel.api.modules.address.application.controllers;

import br.app.mentesaudavel.api.modules.address.application.data.request.UpdateAddressRequestDTO;
import br.app.mentesaudavel.api.modules.address.application.services.UpdateAddressService;
import br.app.mentesaudavel.api.modules.security.helpers.AuthenticationHelper;
import br.app.mentesaudavel.api.modules.user.domain.model.User;
import br.app.mentesaudavel.api.shared.dto.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final UpdateAddressService updateAddressService;

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponseDTO<Void>> updateAddress(
            @PathVariable String id,
            @Valid @RequestBody UpdateAddressRequestDTO data
    ) {
        User user = AuthenticationHelper.getAuthenticatedUser();
        this.updateAddressService.execute(user, UUID.fromString(id), data);

        ApplicationResponseDTO<Void> response = ApplicationResponseDTO
                .<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Address updated successfully.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
