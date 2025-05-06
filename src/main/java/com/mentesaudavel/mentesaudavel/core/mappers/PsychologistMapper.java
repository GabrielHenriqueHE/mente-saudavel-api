package com.mentesaudavel.mentesaudavel.core.mappers;

import com.mentesaudavel.mentesaudavel.core.dto.out.PsychologistProfileResponseDTO;
import com.mentesaudavel.mentesaudavel.core.entities.Psychologist;

public class PsychologistMapper {

    public static PsychologistProfileResponseDTO entityToDTO(Psychologist psychologist) {
        return new PsychologistProfileResponseDTO(
                psychologist.getId(),
                psychologist.getName(),
                psychologist.getCrp(),
                psychologist.getBirthDate(),
                psychologist.getActivitiesStartDate(),
                psychologist.getAbout(),
                psychologist.getCreatedAt(),
                psychologist.getUpdatedAt()
        );
    }
}
