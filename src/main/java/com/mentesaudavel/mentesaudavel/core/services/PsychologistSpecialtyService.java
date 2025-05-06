package com.mentesaudavel.mentesaudavel.core.services;

import com.mentesaudavel.mentesaudavel.core.entities.Psychologist;
import com.mentesaudavel.mentesaudavel.core.entities.PsychologistSpecialty;
import com.mentesaudavel.mentesaudavel.core.entities.Specialty;
import com.mentesaudavel.mentesaudavel.core.entities.User;
import com.mentesaudavel.mentesaudavel.core.entities.embeddable.PsychologistSpecialtyId;
import com.mentesaudavel.mentesaudavel.core.exceptions.ResourceNotFoundException;
import com.mentesaudavel.mentesaudavel.core.exceptions.UnprocessableEntityException;
import com.mentesaudavel.mentesaudavel.core.repositories.PsychologistRepository;
import com.mentesaudavel.mentesaudavel.core.repositories.PsychologistSpecialtyRepository;
import com.mentesaudavel.mentesaudavel.core.repositories.SpecialtyRepository;
import com.mentesaudavel.mentesaudavel.core.security.implementations.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PsychologistSpecialtyService {

    @Autowired
    private PsychologistSpecialtyRepository psychologistSpecialtyRepository;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private PsychologistRepository psychologistRepository;

    public void linkPsychologistToSpecialty(Long specialtyId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = userDetails.getUser();

        Psychologist psychologist = this.psychologistRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Psychologist not found from user."));

        Specialty specialty = this.specialtyRepository.findById(specialtyId)
                .orElseThrow(() -> new ResourceNotFoundException("Specialty not found from ID: ".concat(String.valueOf(specialtyId))));

        PsychologistSpecialtyId id = new PsychologistSpecialtyId(psychologist.getId(), specialtyId);

        boolean exists = this.psychologistSpecialtyRepository.existsById(id);
        if (exists) {
            throw new UnprocessableEntityException("Relationship already exists.");
        }

        PsychologistSpecialty link = new PsychologistSpecialty(id, psychologist, specialty);
        this.psychologistSpecialtyRepository.save(link);
    }

    public void unlinkPsychologistFromSpecialty(Long specialtyId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = userDetails.getUser();

        Psychologist psychologist = this.psychologistRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Psychologist not found from user."));

        Specialty specialty = this.specialtyRepository.findById(specialtyId)
                .orElseThrow(() -> new ResourceNotFoundException("Specialty not found from ID: ".concat(String.valueOf(specialtyId))));

        PsychologistSpecialtyId id = new PsychologistSpecialtyId(psychologist.getId(), specialtyId);

        PsychologistSpecialty psychologistSpecialty = this.psychologistSpecialtyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Psychologist specialty relationship not found."));

        this.psychologistSpecialtyRepository.delete(psychologistSpecialty);
    }
}
