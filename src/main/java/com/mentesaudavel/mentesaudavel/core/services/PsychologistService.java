package com.mentesaudavel.mentesaudavel.core.services;

import com.mentesaudavel.mentesaudavel.core.dto.in.ContactCreateRequestDTO;
import com.mentesaudavel.mentesaudavel.core.dto.in.PsychologistCreateRequestDTO;
import com.mentesaudavel.mentesaudavel.core.dto.out.PsychologistCreateResponseDTO;
import com.mentesaudavel.mentesaudavel.core.entities.Psychologist;
import com.mentesaudavel.mentesaudavel.core.entities.User;
import com.mentesaudavel.mentesaudavel.core.exceptions.ResourceNotFoundException;
import com.mentesaudavel.mentesaudavel.core.exceptions.UnprocessableEntityException;
import com.mentesaudavel.mentesaudavel.core.mappers.PsychologistMapper;
import com.mentesaudavel.mentesaudavel.core.repositories.PsychologistRepository;
import com.mentesaudavel.mentesaudavel.core.security.implementations.UserDetailsImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PsychologistService {

    @Autowired
    private PsychologistRepository psychologistRepository;

    @Autowired
    private ContactService contactService;

    @Transactional
    public PsychologistCreateResponseDTO createPsychologist(PsychologistCreateRequestDTO dto) {
        UserDetailsImpl authenticatedUserDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = authenticatedUserDetails.getUser();

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

    @Transactional
    public void addContactsToAuthenticatedPsychologist(List<ContactCreateRequestDTO> contacts) {
        UserDetailsImpl authenticatedUserDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = authenticatedUserDetails.getUser();

        Psychologist psychologist = psychologistRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Psychologist profile not found."));

        var updatedContacts = this.contactService.registerContacts(psychologist, contacts);
        psychologist.setContacts(updatedContacts);

        this.psychologistRepository.save(psychologist);
    }
}
