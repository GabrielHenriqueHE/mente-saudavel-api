package br.app.mentesaudavel.api.modules.psychologist.mappers;

import br.app.mentesaudavel.api.modules.psychologist.application.data.response.GetPsychologistProfileResponseDTO;
import br.app.mentesaudavel.api.modules.psychologist.domain.model.Psychologist;

public class PsychologistMapper {

    public static GetPsychologistProfileResponseDTO mapToDTO(Psychologist psychologist) {
        return new GetPsychologistProfileResponseDTO(
                psychologist.getId(),
                psychologist.getName(),
                psychologist.getCrp(),
                psychologist.getBirthDate(),
                psychologist.getActivitiesStartDate(),
                psychologist.getAbout(),
                psychologist.getProfilePicture(),
                psychologist.getSlug(),
                psychologist.getCreatedAt(),
                psychologist.getUpdatedAt()
        );
    }
}
