package com.mentesaudavel.mentesaudavel.core.services;

import com.mentesaudavel.mentesaudavel.core.dto.in.PsychologistCreateDTO;
import com.mentesaudavel.mentesaudavel.core.dto.out.PsychologistCreationResponseDTO;
import com.mentesaudavel.mentesaudavel.core.entities.Psychologist;
import com.mentesaudavel.mentesaudavel.core.entities.User;
import com.mentesaudavel.mentesaudavel.core.exceptions.BadRequestException;
import com.mentesaudavel.mentesaudavel.core.exceptions.UnprocessableEntityException;
import com.mentesaudavel.mentesaudavel.core.repositories.PsychologistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PsychologistService {

    @Autowired
    private PsychologistRepository psychologistRepository;

    public PsychologistCreationResponseDTO createPsychologist(User user, PsychologistCreateDTO dto) {
        if (user == null) {
            throw new BadRequestException("No user provided.");
        }

        var psychologistExists = this.psychologistRepository.findByUser(user);

        if (psychologistExists.isPresent()) {
            throw new UnprocessableEntityException("User already has a psychologist profile.");
        }

        Psychologist psychologist = new Psychologist(
                dto.name(),
                dto.crp(),
                dto.birthDate(),
                dto.activitiesStartDate(),
                user
        );

        var createdPsychologist = this.psychologistRepository.save(psychologist);

        return new PsychologistCreationResponseDTO(
                createdPsychologist.getId(),
                createdPsychologist.getName(),
                createdPsychologist.getCrp(),
                createdPsychologist.getBirthDate(),
                createdPsychologist.getActivitiesStartDate()
        );
    }
}
