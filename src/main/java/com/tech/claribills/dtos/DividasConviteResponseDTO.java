package com.tech.claribills.dtos;

import com.tech.claribills.entity.ParticipanteDividas;
import com.tech.claribills.entity.ParticipanteDividasStatus;

import java.time.LocalDateTime;

public record DividasConviteResponseDTO(Integer id, DividaResponseDTO divida, ParticipanteDividasStatus status, LocalDateTime createdAt) {

    public DividasConviteResponseDTO(ParticipanteDividas p){
        this(
                p.getId(),
                new DividaResponseDTO(p.getDivida()),
                p.getStatus(),
                p.getCreatedAt()
        );
    }

}
