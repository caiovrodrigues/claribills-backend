package com.tech.claribills.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequestDTO(@NotBlank String login, @NotBlank String password) {
}
