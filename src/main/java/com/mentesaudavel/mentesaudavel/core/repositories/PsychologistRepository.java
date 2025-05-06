package com.mentesaudavel.mentesaudavel.core.repositories;

import com.mentesaudavel.mentesaudavel.core.entities.Psychologist;
import com.mentesaudavel.mentesaudavel.core.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PsychologistRepository extends JpaRepository<Psychologist, UUID> {

    Optional<Psychologist> findByUser(User user);
}
