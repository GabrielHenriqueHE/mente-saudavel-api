package br.app.mentesaudavel.api.modules.psychologist.application.data.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreatePsychologistRequestDTO(
   @NotBlank(message = "Name is required.")
   @Size(max = 100, message = "Name must be at most 100 characters.")
   String name,

   @NotBlank(message = "CRP is required.")
   @Pattern(regexp = "^\\d{2}/\\d{5}$", message = "CRP must be in the format UF/XXXXX.")
   String crp,

   @NotNull(message = "Birth date is required.")
   @Past(message = "Birth date must be in the past.")
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
   LocalDate birthDate,

   @NotNull(message = "Activities start date is required.")
   @PastOrPresent(message = "Activities start date must be past or present.")
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
   LocalDate activitiesStartDate,

   @Size(max = 2600, message = "About text must be at most 2600 characters.")
   String about,

   @Size(max = 255, message = "Profile picture link must be at most 255 characters.")
   String profilePicture,

   @NotBlank(message = "Slug is required.")
   @Size(min= 3, max = 50, message = "Slug must be at most 50 characters.")
   String slug
) {}
