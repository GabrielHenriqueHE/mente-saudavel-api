package com.mentesaudavel.mentesaudavel.core.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_psychologists")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Psychologist implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "crp", nullable = false, unique = true, length = 8)
    private String crp;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "activities_start_date", nullable = false)
    private LocalDate activitiesStartDate;

    @Column(name = "about")
    @Lob
    private String about;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(
            mappedBy = "psychologist",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<Contact> contacts = new HashSet<>();

    @OneToMany(
            mappedBy = "psychologist",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(
            mappedBy = "psychologist",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<PsychologistSpecialty> specialties = new HashSet<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Psychologist(String name, String crp, LocalDate birthDate, LocalDate activitiesStartDate, User user) {
        this.name = name;
        this.crp = crp;
        this.birthDate = birthDate;
        this.activitiesStartDate = activitiesStartDate;
        this.user = user;
    }
}
