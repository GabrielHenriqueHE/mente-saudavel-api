package br.app.mentesaudavel.api.modules.specialization.application.data.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class LinkSpecializationToPsychologistResponseDTO {
    Integer numberOfSpecializationsAdded;
    Map<String, String[]> errors;
}