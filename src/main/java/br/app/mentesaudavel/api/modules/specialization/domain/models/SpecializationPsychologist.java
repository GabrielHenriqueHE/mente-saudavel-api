package br.app.mentesaudavel.api.modules.specialization.domain.models;

import br.app.mentesaudavel.api.modules.psychologist.domain.model.Psychologist;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "tb_psychologists_specializations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpecializationPsychologist implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private SpecializationPsychologistId id;

    @ManyToOne
    @JoinColumn(name = "psychologist_id")
    @MapsId("psychologistId")
    private Psychologist psychologist;

    @ManyToOne
    @JoinColumn(name = "specialization_id")
    @MapsId("specializationId")
    private Specialization specialization;
}
