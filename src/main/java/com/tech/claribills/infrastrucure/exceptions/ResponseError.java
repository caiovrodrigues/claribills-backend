package com.tech.claribills.infrastrucure.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

public record ResponseError(LocalDateTime timestamp, String path, Integer statusCode, String message, @JsonInclude(value = JsonInclude.Include.NON_NULL) Map<String, String> fieldErrors) {

    public ResponseError(LocalDateTime timestamp, String path, Integer statusCode, String message){
        this(timestamp, path, statusCode, message, null);
    }

}
