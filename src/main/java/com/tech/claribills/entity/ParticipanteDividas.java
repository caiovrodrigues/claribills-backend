package com.tech.claribills.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"usuario", "divida"}))
@Entity
public class ParticipanteDividas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @JoinColumn(name = "usuario")
    @ManyToOne
    private Usuario usuario;

    @JsonIgnore
    @NotNull
    @JoinColumn(name = "divida")
    @ManyToOne
    private Divida divida;

    @JoinColumn(name = "status")
    @ManyToOne
    private ParticipanteDividasStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
