package br.app.mentesaudavel.api.modules.specialization.application.services;

import br.app.mentesaudavel.api.modules.psychologist.domain.model.Psychologist;
import br.app.mentesaudavel.api.modules.psychologist.repositories.PsychologistRepository;
import br.app.mentesaudavel.api.modules.specialization.domain.models.Specialization;
import br.app.mentesaudavel.api.modules.specialization.domain.models.SpecializationPsychologist;
import br.app.mentesaudavel.api.modules.specialization.domain.models.SpecializationPsychologistId;
import br.app.mentesaudavel.api.modules.specialization.repositories.SpecializationPsychologistRepository;
import br.app.mentesaudavel.api.modules.specialization.repositories.SpecializationRepository;
import br.app.mentesaudavel.api.modules.user.domain.model.User;
import br.app.mentesaudavel.api.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnlinkSpecializationFromPsychologistService {

    private final PsychologistRepository psychologistRepository;
    private final SpecializationRepository specializationRepository;
    private final SpecializationPsychologistRepository specializationPsychologistRepository;

    public void execute(User user, Long specializationId) {
        Psychologist psychologist = this.psychologistRepository
                .findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Psychologist profile not found.", null));

        Specialization specialization = this.specializationRepository
                .findById(specializationId)
                .orElseThrow(() -> new ResourceNotFoundException("Specialization not found.", null));

        SpecializationPsychologistId relationshipId = new SpecializationPsychologistId(psychologist.getId(), specializationId);

        SpecializationPsychologist relationship = this.specializationPsychologistRepository
                .findById(relationshipId)
                .orElseThrow(() -> new ResourceNotFoundException("Relationship not found.", null));

        this.specializationPsychologistRepository.delete(relationship);
    }
}
