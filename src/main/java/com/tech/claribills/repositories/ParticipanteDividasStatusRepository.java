package com.tech.claribills.repositories;

import com.tech.claribills.entity.ParticipanteDividasStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipanteDividasStatusRepository extends JpaRepository<ParticipanteDividasStatus, Integer> {

    ParticipanteDividasStatus findByStatus(String name);

}
