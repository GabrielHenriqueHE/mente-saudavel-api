package com.mentesaudavel.mentesaudavel.core.entities;

import com.mentesaudavel.mentesaudavel.core.entities.embeddable.PsychologistSpecialtyId;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "tb_psychologists_specialties")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PsychologistSpecialty implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PsychologistSpecialtyId id;

    @ManyToOne
    @JoinColumn(name = "psychologist_id")
    @MapsId("psychologistId")
    private Psychologist psychologist;

    @ManyToOne
    @JoinColumn(name = "specialty_id")
    @MapsId("specialtyId")
    private Specialty specialty;
}
