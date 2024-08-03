package com.tech.claribills.dtos;

import com.tech.claribills.entity.Cartao;

public record DividaAtualizarDTO(String name, String description, Integer numberInstallments, Integer paidInstallments, Cartao cartao) {
}
