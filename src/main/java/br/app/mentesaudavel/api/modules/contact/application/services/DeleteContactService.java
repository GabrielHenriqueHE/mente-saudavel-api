package br.app.mentesaudavel.api.modules.contact.application.services;

import br.app.mentesaudavel.api.modules.contact.domain.model.Contact;
import br.app.mentesaudavel.api.modules.contact.repositories.ContactRepository;
import br.app.mentesaudavel.api.modules.psychologist.domain.model.Psychologist;
import br.app.mentesaudavel.api.modules.psychologist.repositories.PsychologistRepository;
import br.app.mentesaudavel.api.modules.user.domain.model.User;
import br.app.mentesaudavel.api.shared.exceptions.ForbiddenOperationException;
import br.app.mentesaudavel.api.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteContactService {

    private final ContactRepository contactRepository;
    private final PsychologistRepository psychologistRepository;

    public void execute(User user, UUID id) {
        Psychologist psychologist = this.psychologistRepository
                .findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Psychologist profile not found.", null));

        Contact contact = this.contactRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact registry not found.", null));

        if (!contact.getPsychologist().equals(psychologist)) {
            throw new ForbiddenOperationException("You don't own this contact registry.", null);
        }

        this.contactRepository.delete(contact);
    }
}
