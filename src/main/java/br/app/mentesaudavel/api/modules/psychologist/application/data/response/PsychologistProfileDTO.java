package br.app.mentesaudavel.api.modules.psychologist.application.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record PsychologistProfileDTO(
        @JsonProperty("id")
        UUID id,

        @JsonProperty("name")
        String name,

        @JsonProperty("crp")
        String crp,

        @JsonProperty("birthDate")
        LocalDate birthDate,

        @JsonProperty("activitiesStartDate")
        LocalDate activitiesStartDate,

        @JsonProperty("about")
        String about,

        @JsonProperty("profilePicture")
        String profilePicture,

        @JsonProperty("slug")
        String slug,

        @JsonProperty("createdAt")
        LocalDateTime createdAt,

        @JsonProperty("updatedAt")
        LocalDateTime updatedAt
) {
}
