package com.mentesaudavel.mentesaudavel.core.repositories;

import com.mentesaudavel.mentesaudavel.core.entities.PsychologistSpecialty;
import com.mentesaudavel.mentesaudavel.core.entities.embeddable.PsychologistSpecialtyId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PsychologistSpecialtyRepository extends JpaRepository<PsychologistSpecialty, PsychologistSpecialtyId> {
}
