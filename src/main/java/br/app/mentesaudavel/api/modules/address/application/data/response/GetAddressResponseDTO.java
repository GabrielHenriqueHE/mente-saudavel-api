package br.app.mentesaudavel.api.modules.address.application.data.response;

import br.app.mentesaudavel.api.modules.address.domain.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class GetAddressResponseDTO {
    UUID id;
    String description;
    String cep;
    String street;
    String complement;
    String neighborhood;
    Integer number;
    String city;
    String state;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public static GetAddressResponseDTO mapToDTO(Address address) {
        return new GetAddressResponseDTO(
                address.getId(),
                address.getDescription(),
                address.getCep(),
                address.getStreet(),
                address.getComplement(),
                address.getNeighborhood(),
                address.getNumber(),
                address.getCity(),
                address.getState(),
                address.getCreatedAt(),
                address.getUpdatedAt()
        );
    }
}
