package com.tech.claribills.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    public enum Values{
        ADMIN(1),
        USER(2);

        final Integer id;

        Values(Integer id){
            this.id = id;
        }
    }

}
