package br.app.mentesaudavel.api.modules.contact.application.data.response;

import br.app.mentesaudavel.api.modules.contact.domain.model.Contact;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetContactResponseDTO {
    String description;
    String email;
    String phoneNumber;
    String linkedin;

    public static GetContactResponseDTO mapToDTO(Contact contact) {
        return GetContactResponseDTO
                .builder()
                .description(contact.getDescription())
                .email(contact.getEmail())
                .phoneNumber(contact.getPhoneNumber())
                .linkedin(contact.getLinkedin())
                .build();
    }
}
