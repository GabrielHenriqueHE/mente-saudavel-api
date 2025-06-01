package br.app.mentesaudavel.api.modules.address.application.services;

import br.app.mentesaudavel.api.modules.address.application.data.response.GetAddressResponseDTO;
import br.app.mentesaudavel.api.modules.address.repositories.AddressRepository;
import br.app.mentesaudavel.api.modules.psychologist.domain.model.Psychologist;
import br.app.mentesaudavel.api.modules.psychologist.repositories.PsychologistRepository;
import br.app.mentesaudavel.api.modules.user.domain.model.User;
import br.app.mentesaudavel.api.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAuthenticatedPsychologistAddressesService {

    private final AddressRepository addressRepository;
    private final PsychologistRepository psychologistRepository;

    public List<GetAddressResponseDTO> execute(User user) {
        Psychologist psychologist = this.psychologistRepository
                .findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Psychologist profile not found.", null));

        return this.addressRepository
                .findAllByPsychologist(psychologist)
                .stream()
                .map(GetAddressResponseDTO::mapToDTO).toList();
    }
}
