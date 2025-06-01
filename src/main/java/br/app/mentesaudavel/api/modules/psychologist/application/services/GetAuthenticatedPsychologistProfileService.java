package br.app.mentesaudavel.api.modules.psychologist.application.services;

import br.app.mentesaudavel.api.modules.psychologist.application.data.response.GetPsychologistProfileResponseDTO;
import br.app.mentesaudavel.api.modules.psychologist.domain.model.Psychologist;
import br.app.mentesaudavel.api.modules.psychologist.mappers.PsychologistMapper;
import br.app.mentesaudavel.api.modules.psychologist.repositories.PsychologistRepository;
import br.app.mentesaudavel.api.modules.user.domain.model.User;
import br.app.mentesaudavel.api.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAuthenticatedPsychologistProfileService {

    private final PsychologistRepository psychologistRepository;

    public GetPsychologistProfileResponseDTO execute(User user) {
        Psychologist psychologist = this.psychologistRepository
                .findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Psychologist profile not found.", null));

        return PsychologistMapper.mapToDTO(psychologist);
    }
}
