package com.mentesaudavel.mentesaudavel.core.controllers;

import com.mentesaudavel.mentesaudavel.core.dto.in.AddressUpdateRequestDTO;
import com.mentesaudavel.mentesaudavel.core.dto.out.AppResponse;
import com.mentesaudavel.mentesaudavel.core.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;


    @PutMapping("/{id}")
    public ResponseEntity<AppResponse<Void>> updateAddress(
            @PathVariable UUID id,
            @RequestBody @Valid AddressUpdateRequestDTO dto
    ) {
        this.addressService.updateAddress(id, dto);

        AppResponse<Void> response = new AppResponse<>(
                HttpStatus.OK.value(),
                "Address updated successfully",
                null,
                null
        );

        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(
            @PathVariable UUID id
    ) {
        this.addressService.deleteAddress(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
