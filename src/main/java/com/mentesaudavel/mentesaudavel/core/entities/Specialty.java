package com.mentesaudavel.mentesaudavel.core.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_specialties")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Specialty implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    @Column(name = "name", unique = true, length = 50)
    private String name;

    @OneToMany(mappedBy = "specialty", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PsychologistSpecialty> psychologists = new HashSet<>();
}
