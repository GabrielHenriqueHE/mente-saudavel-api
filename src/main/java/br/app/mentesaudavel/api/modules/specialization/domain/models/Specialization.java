package br.app.mentesaudavel.api.modules.specialization.domain.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_specializations")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Specialization implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    @Id
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "name", unique = true, length = 60)
    private String name;

    @OneToMany(mappedBy = "specialization", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SpecializationPsychologist> psychologists = new HashSet<>();
}
