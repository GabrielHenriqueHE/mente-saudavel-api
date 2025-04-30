package com.mentesaudavel.mentesaudavel.core.services;

import com.mentesaudavel.mentesaudavel.core.dto.in.PsychologistCreateRequestDTO;
import com.mentesaudavel.mentesaudavel.core.dto.out.PsychologistCreateResponseDTO;
import com.mentesaudavel.mentesaudavel.core.entities.Psychologist;
import com.mentesaudavel.mentesaudavel.core.entities.User;
import com.mentesaudavel.mentesaudavel.core.exceptions.BadRequestException;
import com.mentesaudavel.mentesaudavel.core.exceptions.UnprocessableEntityException;
import com.mentesaudavel.mentesaudavel.core.mappers.PsychologistMapper;
import com.mentesaudavel.mentesaudavel.core.repositories.PsychologistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PsychologistService {

    @Autowired
    private PsychologistRepository psychologistRepository;

    @Transactional
    public PsychologistCreateResponseDTO createPsychologist(User user, PsychologistCreateRequestDTO dto) {
        if (user == null) {
            throw new BadRequestException("No user provided.");
        }

        var psychologistExists = this.psychologistRepository.findByUser(user);

        if (psychologistExists.isPresent()) {
            throw new UnprocessableEntityException("User already has a psychologist profile.");
        }

        LocalDate now = LocalDate.now();
        boolean userIsUnderTwentyYearsOld = dto.birthDate().isAfter(now.minusYears(20));

        if (dto.birthDate().isAfter(now)) {
            throw new UnprocessableEntityException("Birth date cannot be in the future.");
        } else if (dto.birthDate().isBefore(now.minusYears(100))) {
            throw new UnprocessableEntityException("Birth date is too old.");
        } else if (userIsUnderTwentyYearsOld) {
            throw new UnprocessableEntityException("Psychologist must be at least 20 years old.");
        }


        Psychologist psychologist = new Psychologist(
                dto.name(),
                dto.crp(),
                dto.birthDate(),
                dto.activitiesStartDate(),
                user
        );

        if (dto.about() != null) {
            psychologist.setAbout(dto.about());
        }

        var createdPsychologist = this.psychologistRepository.save(psychologist);

        return PsychologistMapper.entityToDTO(createdPsychologist);
    }
}
