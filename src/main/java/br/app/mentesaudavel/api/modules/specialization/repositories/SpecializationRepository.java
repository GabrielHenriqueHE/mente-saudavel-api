package br.app.mentesaudavel.api.modules.specialization.repositories;

import br.app.mentesaudavel.api.modules.specialization.domain.models.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
}
