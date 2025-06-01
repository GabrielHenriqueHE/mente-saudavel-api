package br.app.mentesaudavel.api.modules.contact.application.services;

import br.app.mentesaudavel.api.modules.contact.application.data.response.GetContactResponseDTO;
import br.app.mentesaudavel.api.modules.contact.repositories.ContactRepository;
import br.app.mentesaudavel.api.modules.psychologist.domain.model.Psychologist;
import br.app.mentesaudavel.api.modules.psychologist.repositories.PsychologistRepository;
import br.app.mentesaudavel.api.modules.user.domain.model.User;
import br.app.mentesaudavel.api.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAuthenticatedPsychologistContactsService {

    private final ContactRepository contactRepository;
    private final PsychologistRepository psychologistRepository;

    public List<GetContactResponseDTO> execute(User user) {
        Psychologist psychologist = this.psychologistRepository
                .findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Psychologist profile not found.", null));

        return this.contactRepository.findByPsychologist(psychologist)
                .stream()
                .map(GetContactResponseDTO::mapToDTO)
                .toList();
    }
}
