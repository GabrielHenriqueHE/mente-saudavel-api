package com.mentesaudavel.mentesaudavel.core.repositories;

import com.mentesaudavel.mentesaudavel.core.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
