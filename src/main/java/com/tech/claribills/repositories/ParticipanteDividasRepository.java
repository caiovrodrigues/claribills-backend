package com.tech.claribills.repositories;

import com.tech.claribills.entity.ParticipanteDividas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipanteDividasRepository extends JpaRepository<ParticipanteDividas, Integer> {
}
