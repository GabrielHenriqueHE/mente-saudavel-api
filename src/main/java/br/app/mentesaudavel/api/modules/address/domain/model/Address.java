package br.app.mentesaudavel.api.modules.address.domain.model;

import br.app.mentesaudavel.api.modules.psychologist.domain.model.Psychologist;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_addresses")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Address implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "description", nullable = false, length = 30)
    private String description;

    @Column(name = "cep", nullable = false, length = 8)
    private String cep;

    @Column(name = "complement", length = 100)
    private String complement;

    @Column(name = "street", length = 100)
    private String street;

    @Column(name = "neighborhood", nullable = false, length = 100)
    private String neighborhood;

    @Column(name = "number", nullable = false)
    @EqualsAndHashCode.Include
    private Integer number;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "state", nullable = false, length = 2)
    private String state;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "psychologist_id", nullable = false)
    private Psychologist psychologist;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Address(
            String description,
            String cep,
            String street,
            String neighborhood,
            Integer number,
            String city,
            String state
    ) {
        this.description = description;
        this.cep = cep;
        this.street = street;
        this.neighborhood = neighborhood;
        this.number = number;
        this.city = city;
        this.state = state;
    }
}
