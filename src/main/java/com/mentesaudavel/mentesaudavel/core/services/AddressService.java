package com.mentesaudavel.mentesaudavel.core.services;

import com.mentesaudavel.mentesaudavel.core.dto.in.AddressCreateRequestDTO;
import com.mentesaudavel.mentesaudavel.core.dto.in.AddressUpdateRequestDTO;
import com.mentesaudavel.mentesaudavel.core.entities.Address;
import com.mentesaudavel.mentesaudavel.core.entities.Psychologist;
import com.mentesaudavel.mentesaudavel.core.entities.User;
import com.mentesaudavel.mentesaudavel.core.exceptions.ForbiddenOperationException;
import com.mentesaudavel.mentesaudavel.core.exceptions.ResourceNotFoundException;
import com.mentesaudavel.mentesaudavel.core.repositories.AddressRepository;
import com.mentesaudavel.mentesaudavel.core.security.implementations.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Set<Address> registerAddresses(Psychologist psychologist, List<AddressCreateRequestDTO> addressesDTO) {
        Set<Address> addresses = psychologist.getAddresses();

        for (AddressCreateRequestDTO dto : addressesDTO) {
            Address address = this.mapToEntity(dto);

            address.setPsychologist(psychologist);

            addresses.add(address);
        }

        return addresses;
    }

    public void updateAddress(UUID id, AddressUpdateRequestDTO dto) {
        Address address = this.addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address register not found from ID: ".concat(String.valueOf(id))));

        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User authenticatedUser = userDetailsImpl.getUser();

        if (!address.getPsychologist().getUser().getId().equals(authenticatedUser.getId())) {
            throw new ForbiddenOperationException("You don't own this address.");
        }

        if (dto.description() != null && !dto.description().isBlank()) {
            address.setDescription(dto.description());
        }

        if (dto.cep() != null && !dto.cep().isBlank()) {
            address.setCep(dto.cep());
        }

        if (dto.street() != null && !dto.street().isBlank()) {
            address.setStreet(dto.street());
        }

        if (dto.complement() != null && !dto.complement().isBlank()) {
            address.setComplement(dto.complement());
        }

        if (dto.neighborhood() != null && !dto.neighborhood().isBlank()) {
            address.setNeighborhood(dto.neighborhood());
        }

        if (dto.number() != null) {
            address.setNumber(dto.number());
        }

        if (dto.city() != null && !dto.city().isBlank()) {
            address.setCity(dto.city());
        }

        if (dto.state() != null && !dto.state().isBlank()) {
            address.setState(dto.state());
        }

        this.addressRepository.save(address);
    }

    public void deleteAddress(UUID id) {
        Address address = this.addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address register not found from ID: ".concat(String.valueOf(id))));

        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User authenticatedUser = userDetailsImpl.getUser();

        if (!address.getPsychologist().getUser().getId().equals(authenticatedUser.getId())) {
            throw new ForbiddenOperationException("You don't own this address.");
        }

        this.addressRepository.delete(address);
    }

    private Address mapToEntity(AddressCreateRequestDTO dto) {
        Address address = new Address(
                dto.description(),
                dto.cep(),
                dto.street(),
                dto.neighborhood(),
                dto.number(),
                dto.city(),
                dto.state()
        );

        address.setComplement(dto.complement());

        return address;
    }
}
