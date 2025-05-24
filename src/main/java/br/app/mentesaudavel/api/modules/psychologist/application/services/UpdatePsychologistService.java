package br.app.mentesaudavel.api.modules.psychologist.application.services;

import br.app.mentesaudavel.api.modules.psychologist.application.data.request.UpdatePsychologistRequestDTO;
import br.app.mentesaudavel.api.modules.psychologist.domain.model.Psychologist;
import br.app.mentesaudavel.api.modules.psychologist.repositories.PsychologistRepository;
import br.app.mentesaudavel.api.modules.user.domain.model.User;
import br.app.mentesaudavel.api.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePsychologistService {

    private final PsychologistRepository psychologistRepository;

    public void execute(User user, UpdatePsychologistRequestDTO data) {
        Psychologist psychologist = this.psychologistRepository
                .findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("User not found.", null));

        if (data.name() != null) {
            psychologist.setName(data.name());
        }

        if (data.about() != null) {
            psychologist.setName(data.about());
        }

        if (data.profilePicture() != null) {
            psychologist.setProfilePicture(data.profilePicture());
        }

        this.psychologistRepository.save(psychologist);
    }
}
