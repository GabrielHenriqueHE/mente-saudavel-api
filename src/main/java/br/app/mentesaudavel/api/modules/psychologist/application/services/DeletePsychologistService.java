package br.app.mentesaudavel.api.modules.psychologist.application.services;

import br.app.mentesaudavel.api.modules.psychologist.domain.model.Psychologist;
import br.app.mentesaudavel.api.modules.psychologist.repositories.PsychologistRepository;
import br.app.mentesaudavel.api.modules.user.domain.model.User;
import br.app.mentesaudavel.api.modules.user.repositories.UserRepository;
import br.app.mentesaudavel.api.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletePsychologistService {

    private final PsychologistRepository psychologistRepository;
    private final UserRepository userRepository;

    public void execute(User user) {
        Psychologist psychologist = this.psychologistRepository
                .findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("User not found.", null));

        this.psychologistRepository.delete(psychologist);

        user.setType(null);
        this.userRepository.save(user);
    }
}
