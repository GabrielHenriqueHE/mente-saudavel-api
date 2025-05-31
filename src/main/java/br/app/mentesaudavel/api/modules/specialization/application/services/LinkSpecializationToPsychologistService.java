package br.app.mentesaudavel.api.modules.specialization.application.services;

import br.app.mentesaudavel.api.modules.psychologist.domain.model.Psychologist;
import br.app.mentesaudavel.api.modules.psychologist.repositories.PsychologistRepository;
import br.app.mentesaudavel.api.modules.specialization.application.data.request.LinkSpecializationToPsychologistRequestDTO;
import br.app.mentesaudavel.api.modules.specialization.application.data.response.LinkSpecializationToPsychologistResponseDTO;
import br.app.mentesaudavel.api.modules.specialization.domain.models.Specialization;
import br.app.mentesaudavel.api.modules.specialization.domain.models.SpecializationPsychologist;
import br.app.mentesaudavel.api.modules.specialization.domain.models.SpecializationPsychologistId;
import br.app.mentesaudavel.api.modules.specialization.repositories.SpecializationPsychologistRepository;
import br.app.mentesaudavel.api.modules.specialization.repositories.SpecializationRepository;
import br.app.mentesaudavel.api.modules.user.domain.model.User;
import br.app.mentesaudavel.api.shared.exceptions.ResourceNotFoundException;
import br.app.mentesaudavel.api.shared.exceptions.UnprocessableEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LinkSpecializationToPsychologistService {

    private final PsychologistRepository psychologistRepository;
    private final SpecializationRepository specializationRepository;
    private final SpecializationPsychologistRepository specializationPsychologistRepository;

    public LinkSpecializationToPsychologistResponseDTO execute(User user, LinkSpecializationToPsychologistRequestDTO data) {
        Psychologist psychologist = this.psychologistRepository
                .findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Psychologist profile not found.", null));

        int successes = 0;
        Map<String, String[]> errors = new HashMap<>();

        for (Long inputSpecializationId : data.specializations()) {
            Optional<Specialization> optionalSpecialization = this.specializationRepository.findById(inputSpecializationId);

            if (optionalSpecialization.isEmpty()) {
                errors.put(String.valueOf(inputSpecializationId), new String[]{"Specialization not found from id."});
                continue;
            }

            Specialization specialization = optionalSpecialization.get();

            SpecializationPsychologistId id = new SpecializationPsychologistId(psychologist.getId(), inputSpecializationId);

            boolean exists = this.specializationPsychologistRepository.existsById(id);

            if (exists) {
                errors.put(String.valueOf(inputSpecializationId), new String[]{"Relationship already exists."});
                continue;
            }

            SpecializationPsychologist link = new SpecializationPsychologist(id, psychologist, specialization);
            this.specializationPsychologistRepository.save(link);
            successes += 1;
        }

        if (successes == 0) {
            throw new UnprocessableEntityException("Unable to link specializations to psychologist.", errors);
        }

        return LinkSpecializationToPsychologistResponseDTO
                .builder()
                .numberOfSpecializationsAdded(successes)
                .errors(errors)
                .build();
    }
}
