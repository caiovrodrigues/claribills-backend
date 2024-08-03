package com.tech.claribills.infrastrucure.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalHandleExceptions {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseError> entityNotFound(NoSuchElementException ex, HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ResponseError(LocalDateTime.now(), request.getServletPath(), HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

}
