package com.mentesaudavel.mentesaudavel.core.services;

import com.mentesaudavel.mentesaudavel.core.dto.in.AddressCreateRequestDTO;
import com.mentesaudavel.mentesaudavel.core.entities.Address;
import com.mentesaudavel.mentesaudavel.core.entities.Psychologist;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AddressService {

    public Set<Address> registerAddresses(Psychologist psychologist, List<AddressCreateRequestDTO> addressesDTO) {
        Set<Address> addresses = psychologist.getAddresses();

        for (AddressCreateRequestDTO dto : addressesDTO) {
            Address address = this.mapToEntity(dto);

            address.setPsychologist(psychologist);

            addresses.add(address);
        }

        return addresses;
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
