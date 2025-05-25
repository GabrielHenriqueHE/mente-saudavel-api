package br.app.mentesaudavel.api.modules.address.application.services;

import br.app.mentesaudavel.api.modules.address.application.data.request.UpdateAddressRequestDTO;
import br.app.mentesaudavel.api.modules.address.domain.model.Address;
import br.app.mentesaudavel.api.modules.address.repositories.AddressRepository;
import br.app.mentesaudavel.api.modules.psychologist.domain.model.Psychologist;
import br.app.mentesaudavel.api.modules.psychologist.repositories.PsychologistRepository;
import br.app.mentesaudavel.api.modules.user.domain.model.User;
import br.app.mentesaudavel.api.shared.exceptions.ForbiddenOperationException;
import br.app.mentesaudavel.api.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateAddressService {

    private final AddressRepository addressRepository;
    private final PsychologistRepository psychologistRepository;

    public void execute(User user, UUID addressId, UpdateAddressRequestDTO data) {
        Psychologist psychologist = this.psychologistRepository
                .findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Psychologist profile not found.", null));

        Address address = this.addressRepository
                .findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address registry not found.", null));

        if (!address.getPsychologist().equals(psychologist)) {
            throw new ForbiddenOperationException("You don't own this address registry.", null);
        }

        address.setDescription(data.description());
        address.setCep(data.cep());
        address.setStreet(data.street());
        address.setComplement(data.complement());
        address.setNeighborhood(data.neighborhood());
        address.setNumber(data.number());
        address.setCity(data.city());
        address.setState(data.state());

        this.addressRepository.save(address);
    }
}
