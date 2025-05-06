package com.mentesaudavel.mentesaudavel.core.entities.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;


@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PsychologistSpecialtyId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "psychologist_id")
    @EqualsAndHashCode.Include
    private UUID psychologistId;

    @Column(name = "specialty_id")
    @EqualsAndHashCode.Include
    private Long specialtyId;

}
