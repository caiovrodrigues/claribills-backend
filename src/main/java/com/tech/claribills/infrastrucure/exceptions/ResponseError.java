package com.tech.claribills.infrastrucure.exceptions;

import java.time.LocalDateTime;

public record ResponseError(LocalDateTime timestamp, String path, Integer statusCode, String message) {
}
