package br.app.mentesaudavel.api.modules.specialization.repositories;

import br.app.mentesaudavel.api.modules.specialization.domain.models.SpecializationPsychologist;
import br.app.mentesaudavel.api.modules.specialization.domain.models.SpecializationPsychologistId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecializationPsychologistRepository extends JpaRepository<SpecializationPsychologist, SpecializationPsychologistId> {
}
