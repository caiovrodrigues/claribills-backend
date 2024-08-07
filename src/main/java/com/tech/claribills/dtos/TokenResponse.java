package com.tech.claribills.dtos;

import java.time.Instant;

public record TokenResponse (String token, Instant expiresAt){
}
