package com.mentesaudavel.mentesaudavel.core.services;

import com.mentesaudavel.mentesaudavel.core.dto.in.AddressCreateRequestDTO;
import com.mentesaudavel.mentesaudavel.core.dto.in.ContactCreateRequestDTO;
import com.mentesaudavel.mentesaudavel.core.dto.in.PsychologistCreateRequestDTO;
import com.mentesaudavel.mentesaudavel.core.dto.in.PsychologistUpdateRequestDTO;
import com.mentesaudavel.mentesaudavel.core.dto.out.PsychologistProfileResponseDTO;
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
import java.util.UUID;

@Service
public class PsychologistService {

    @Autowired
    private PsychologistRepository psychologistRepository;

    @Autowired
    private ContactService contactService;

    @Autowired
    private AddressService addressService;

    @Transactional
    public PsychologistProfileResponseDTO createPsychologist(PsychologistCreateRequestDTO dto) {
        UserDetailsImpl authenticatedUserDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = authenticatedUserDetails.getUser();

        var psychologistExists = this.psychologistRepository.findByUser(user);

        if (psychologistExists.isPresent()) {
            throw new UnprocessableEntityException("User already has a psychologist profile.");
        }

        this.validateBirthDate(dto.birthDate());
        this.validateActivitiesStartDate(dto.activitiesStartDate(), dto.birthDate());

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

    @Transactional
    public void addAddressesToAuthenticatedPsychologist(List<AddressCreateRequestDTO> dto) {
        UserDetailsImpl authenticatedUserDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = authenticatedUserDetails.getUser();

        Psychologist psychologist = psychologistRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Psychologist profile not found."));

        var updatedAddresses = this.addressService.registerAddresses(psychologist, dto);
        psychologist.setAddresses(updatedAddresses);

        this.psychologistRepository.save(psychologist);
    }

    public void updatePsychologist(PsychologistUpdateRequestDTO dto) {
        UserDetailsImpl authenticatedUserDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = authenticatedUserDetails.getUser();

        Psychologist psychologist = this.psychologistRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Psychologist not found from authenticated user."));

        if (dto.name() != null && !dto.name().isBlank()) {
            psychologist.setName(dto.name());
        }

        if (dto.crp() != null && !dto.crp().isBlank()) {
            psychologist.setCrp(dto.crp());
        }

        if (dto.birthDate() != null) {
            this.validateBirthDate(dto.birthDate());

            psychologist.setBirthDate(dto.birthDate());
        }

        LocalDate birthDate = dto.birthDate() != null ? dto.birthDate() : psychologist.getBirthDate();

        if (dto.activitiesStartDate() != null) {
            this.validateActivitiesStartDate(dto.activitiesStartDate(), birthDate);
        }

        if (dto.about() != null) {
            psychologist.setAbout(dto.about());
        }

        this.psychologistRepository.save(psychologist);
    }

    private void validateBirthDate(LocalDate date) {
        LocalDate now = LocalDate.now();
        boolean userIsUnderTwentyYearsOld = date.isAfter(now.minusYears(20));

        if (date.isAfter(now)) {
            throw new UnprocessableEntityException("Birth date cannot be in the future.");
        } else if (date.isBefore(now.minusYears(100))) {
            throw new UnprocessableEntityException("Birth date is too old.");
        } else if (userIsUnderTwentyYearsOld) {
            throw new UnprocessableEntityException("Psychologist must be at least 20 years old.");
        }
    }

    private void validateActivitiesStartDate(LocalDate activitiesStartDate, LocalDate birthDate) {
        LocalDate now = LocalDate.now();

        if (activitiesStartDate.isAfter(now)) {
            throw new UnprocessableEntityException("Activities start date cannot be in the future.");
        }

        if (activitiesStartDate.isBefore(birthDate.plusYears(20))) {
            throw new UnprocessableEntityException("Must be at least 20 years old to act as a psychologist.");
        }
    }

    public PsychologistProfileResponseDTO getPsychologistDataFromId(UUID id) {
        Psychologist psychologist = this.psychologistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Psychologist profile not found from ID."));

        return PsychologistMapper.entityToDTO(psychologist);
    }
}
