package com.mentesaudavel.mentesaudavel.core.services;

import com.mentesaudavel.mentesaudavel.core.dto.in.ContactCreateRequestDTO;
import com.mentesaudavel.mentesaudavel.core.dto.in.ContactUpdateRequestDTO;
import com.mentesaudavel.mentesaudavel.core.entities.Contact;
import com.mentesaudavel.mentesaudavel.core.entities.Psychologist;
import com.mentesaudavel.mentesaudavel.core.entities.User;
import com.mentesaudavel.mentesaudavel.core.exceptions.BadRequestException;
import com.mentesaudavel.mentesaudavel.core.exceptions.ForbiddenOperationException;
import com.mentesaudavel.mentesaudavel.core.exceptions.ResourceNotFoundException;
import com.mentesaudavel.mentesaudavel.core.repositories.ContactRepository;
import com.mentesaudavel.mentesaudavel.core.security.implementations.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public Set<Contact> registerContacts(Psychologist psychologist, List<ContactCreateRequestDTO> contactsDTO) {
        Set<Contact> contacts = psychologist.getContacts();

        for (ContactCreateRequestDTO dto : contactsDTO) {
            if (dto.email() == null && dto.linkedin() == null && dto.phoneNumber() == null) {
                continue;
            }

            Contact contact = this.createContact(dto);
            contact.setPsychologist(psychologist);
            contacts.add(contact);
        }

        return contacts;
    }

    private Contact createContact(ContactCreateRequestDTO dto) {
        Contact contact = new Contact();

        contact.setDescription(dto.description());
        contact.setEmail(dto.email());
        contact.setLinkedin(dto.linkedin());
        contact.setPhoneNumber(dto.phoneNumber());

        return contact;
    }

    public void updateContact(UUID id, ContactUpdateRequestDTO dto) {
        Contact contact = this.contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact register not found from ID: ".concat(String.valueOf(id))));

        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userDetailsImpl.getUser();

        if (!contact.getPsychologist().getUser().getId().equals(user.getId())) {
            throw new ForbiddenOperationException("You don't own this contact.");
        }

        if (dto.description() != null && !dto.description().isBlank()) {
            contact.setDescription(dto.description());
        }

        if (dto.email() != null) {
            contact.setEmail(dto.email());
        }

        if (dto.linkedin() != null) {
            contact.setLinkedin(dto.linkedin());
        }

        if (dto.phoneNumber() != null) {
            contact.setPhoneNumber(dto.phoneNumber());
        }

        this.contactRepository.save(contact);
    }
}
