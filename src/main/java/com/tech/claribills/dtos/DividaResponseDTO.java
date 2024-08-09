package com.tech.claribills.dtos;

import com.tech.claribills.entity.Cartao;
import com.tech.claribills.entity.Divida;
import com.tech.claribills.entity.ParticipanteDividasStatus;

import java.time.LocalDateTime;
import java.util.List;

public record DividaResponseDTO(
        Integer id,
        String name,
        String description,
        Integer numberInstallments,
        Integer paidInstallments,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        UsuarioResponseDTO owner,
        Cartao cartao,
        List<ParticipantsDTO> participants
) {

    public DividaResponseDTO(Divida divida){
        this(
                divida.getId(),
                divida.getName(),
                divida.getDescription(),
                divida.getNumberInstallments(),
                divida.getPaidInstallments(),
                divida.getCreatedAt(),
                divida.getUpdatedAt(),
                new UsuarioResponseDTO(divida.getOwner().getId(), divida.getOwner().getName()),
                divida.getCartao(),
                divida.getParticipants().stream().map(p -> new ParticipantsDTO(p.getId(), p.getCreatedAt(), p.getStatus(), new UsuarioResponseDTO(p.getUsuario().getId(), p.getUsuario().getName()))).toList()
        );
    }

    record ParticipantsDTO(Integer id, LocalDateTime createdAt, ParticipanteDividasStatus status, UsuarioResponseDTO usuario){}
}

