package com.mentesaudavel.mentesaudavel.core.services;

import com.mentesaudavel.mentesaudavel.core.dto.in.ContactCreateRequestDTO;
import com.mentesaudavel.mentesaudavel.core.entities.Contact;
import com.mentesaudavel.mentesaudavel.core.entities.Psychologist;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ContactService {

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
}
