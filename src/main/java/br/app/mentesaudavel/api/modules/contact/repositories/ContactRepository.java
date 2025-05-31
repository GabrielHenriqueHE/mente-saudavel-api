package br.app.mentesaudavel.api.modules.contact.repositories;

import br.app.mentesaudavel.api.modules.contact.domain.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {
}
