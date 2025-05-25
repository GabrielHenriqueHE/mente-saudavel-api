package br.app.mentesaudavel.api.modules.psychologist.mappers;

import br.app.mentesaudavel.api.modules.psychologist.application.data.response.PsychologistProfileDTO;
import br.app.mentesaudavel.api.modules.psychologist.domain.model.Psychologist;

public class PsychologistMapper {

    public static PsychologistProfileDTO mapToDTO(Psychologist psychologist) {
        return new PsychologistProfileDTO(
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
