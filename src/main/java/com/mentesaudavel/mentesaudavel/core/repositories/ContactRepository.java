package com.mentesaudavel.mentesaudavel.core.repositories;

import com.mentesaudavel.mentesaudavel.core.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {
}
