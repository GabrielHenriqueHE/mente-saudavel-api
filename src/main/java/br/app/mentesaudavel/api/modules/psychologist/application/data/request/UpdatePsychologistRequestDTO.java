package br.app.mentesaudavel.api.modules.psychologist.application.data.request;

import jakarta.validation.constraints.Size;

public record UpdatePsychologistRequestDTO(
        @Size(max = 100, message = "Name must be at most 100 characters.")
        String name,

        @Size(max = 2600, message = "About text must be at most 2600 characters.")
        String about,

        @Size(max = 255, message = "Profile picture link must be at most 255 characters.")
        String profilePicture
) {}
