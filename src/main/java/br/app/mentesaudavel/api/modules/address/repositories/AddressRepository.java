package br.app.mentesaudavel.api.modules.address.repositories;

import br.app.mentesaudavel.api.modules.address.domain.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
