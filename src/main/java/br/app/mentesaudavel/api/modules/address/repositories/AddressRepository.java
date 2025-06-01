package br.app.mentesaudavel.api.modules.address.repositories;

import br.app.mentesaudavel.api.modules.address.domain.model.Address;
import br.app.mentesaudavel.api.modules.psychologist.domain.model.Psychologist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

    List<Address> findAllByPsychologist(Psychologist psychologist);
}
