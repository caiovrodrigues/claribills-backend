package com.tech.claribills.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class ParticipanteDividasStatus {

    public static final String ACCEPTED = "ACCEPTED";
    public static final String PENDING = "PENDING";
    public static final String DENIED = "DENIED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String status;

}
