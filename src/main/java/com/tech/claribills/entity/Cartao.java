package com.tech.claribills.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "owner_name")
    private String ownerName;

    @JoinColumn(name = "banco")
    @ManyToOne
    private Banco banco;

    @Column(name = "dayDueDate")
    private Integer dayDueDate;

}
