package br.app.mentesaudavel.api.modules.psychologist.application.services;

import br.app.mentesaudavel.api.modules.address.application.data.request.CreateAddressRequestDTO;
import br.app.mentesaudavel.api.modules.address.domain.model.Address;
import br.app.mentesaudavel.api.modules.psychologist.domain.model.Psychologist;
import br.app.mentesaudavel.api.modules.psychologist.repositories.PsychologistRepository;
import br.app.mentesaudavel.api.modules.user.domain.model.User;
import br.app.mentesaudavel.api.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CreatePsychologistAddressService {

    private final PsychologistRepository psychologistRepository;

    public void execute(User user, CreateAddressRequestDTO data) {
        Psychologist psychologist = this.psychologistRepository
                .findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Psychologist profile not found.", null));

        Set<Address> psychologistAddresses = psychologist.getAddresses();

        Address address = this.mapToEntity(psychologist, data);
        psychologistAddresses.add(address);

        this.psychologistRepository.save(psychologist);
    }

    private Address mapToEntity(Psychologist psychologist, CreateAddressRequestDTO data) {
        return Address
                .builder()
                .description(data.description())
                .cep(data.cep())
                .street(data.street())
                .complement(data.complement())
                .neighborhood(data.neighborhood())
                .number(data.number())
                .city(data.city())
                .state(data.state())
                .psychologist(psychologist)
                .build();
    }
}
