package com.mentesaudavel.mentesaudavel.core.mappers;

import com.mentesaudavel.mentesaudavel.core.dto.out.PsychologistCreateResponseDTO;
import com.mentesaudavel.mentesaudavel.core.entities.Psychologist;

public class PsychologistMapper {

    public static PsychologistCreateResponseDTO entityToDTO(Psychologist psychologist) {
        return new PsychologistCreateResponseDTO(
                psychologist.getId(),
                psychologist.getName(),
                psychologist.getCrp(),
                psychologist.getAbout(),
                psychologist.getBirthDate(),
                psychologist.getActivitiesStartDate()
        );
    }
}
