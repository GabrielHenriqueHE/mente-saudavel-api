package br.app.mentesaudavel.api.modules.psychologist.repositories;

import br.app.mentesaudavel.api.modules.psychologist.domain.model.Psychologist;
import br.app.mentesaudavel.api.modules.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PsychologistRepository extends JpaRepository<Psychologist, UUID> {

    boolean existsByUser(User user);
    Optional<Psychologist> findByUser(User user);
}
