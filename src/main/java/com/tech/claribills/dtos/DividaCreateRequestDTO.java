package com.tech.claribills.dtos;

import java.math.BigDecimal;

public record DividaCreateRequestDTO(String name, Integer numberInstallments, BigDecimal totalAmount, Integer idUsuario) {
}
