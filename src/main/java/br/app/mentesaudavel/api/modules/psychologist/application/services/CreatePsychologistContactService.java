package br.app.mentesaudavel.api.modules.psychologist.application.services;

import br.app.mentesaudavel.api.modules.contact.application.data.request.CreateContactRequestDTO;
import br.app.mentesaudavel.api.modules.contact.domain.model.Contact;
import br.app.mentesaudavel.api.modules.psychologist.domain.model.Psychologist;
import br.app.mentesaudavel.api.modules.psychologist.repositories.PsychologistRepository;
import br.app.mentesaudavel.api.modules.user.domain.model.User;
import br.app.mentesaudavel.api.shared.exceptions.BadRequestException;
import br.app.mentesaudavel.api.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CreatePsychologistContactService {

    private final PsychologistRepository psychologistRepository;

    public void execute(User user, CreateContactRequestDTO data) {
        Psychologist psychologist = this.psychologistRepository
                .findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Psychologist profile not found.", null));

        Set<Contact> psychologistContacts = psychologist.getContacts();

        Contact contact = this.mapToEntity(psychologist, data);
        psychologistContacts.add(contact);

        this.psychologistRepository.save(psychologist);
    }

    private Contact mapToEntity(Psychologist psychologist, CreateContactRequestDTO data) {
        if (data.email() == null && data.linkedin() == null && data.phoneNumber() == null) {
            throw new BadRequestException("At least one contact type must be provided.", null);
        }

        Map<String, String[]> errors = new HashMap<>();

        if (data.email() != null && data.email().isBlank()) {
            errors.put("email", new String[] {"E-mail must be valid."});
        }

        if (data.linkedin() != null && data.linkedin().isBlank()) {
            errors.put("linkedin", new String[] {"Linkedin must be valid."});
        }

        if (data.phoneNumber() != null && data.phoneNumber().isBlank()) {
            errors.put("phoneNumber", new String[] {"Phone number must be valid."});
        }

        if (!errors.isEmpty()) {
            throw new BadRequestException("Validation error.", errors);
        }

        return Contact
                .builder()
                .description(data.description())
                .email(data.email())
                .linkedin(data.linkedin())
                .phoneNumber(data.phoneNumber())
                .psychologist(psychologist)
                .build();
    }
}
