package com.tech.claribills.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Divida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", length = 600)
    private String description;

    @Column(name = "number_installments")
    private Integer numberInstallments;

    @Column(name = "paid_installments")
    private Integer paidInstallments;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @JoinColumn(name = "owner")
    @ManyToOne
    private Usuario owner;

    @JoinColumn(name = "cartao")
    @ManyToOne(cascade = CascadeType.MERGE)
    private Cartao cartao;

    @OneToMany(mappedBy = "divida", cascade = CascadeType.ALL)
    private Set<ParticipanteDividas> participants;

}
