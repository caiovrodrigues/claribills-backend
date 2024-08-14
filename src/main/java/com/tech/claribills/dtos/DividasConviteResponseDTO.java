package com.tech.claribills.dtos;

import com.tech.claribills.entity.ParticipanteDividasStatus;

import java.time.LocalDateTime;

public record DividasConviteResponseDTO(Integer id, DividaResponseDTO divida, ParticipanteDividasStatus status, LocalDateTime createdAt) {
}
