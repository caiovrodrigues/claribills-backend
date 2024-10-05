package com.tech.claribills.infrastrucure.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalHandleExceptions {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> validationErrors(MethodArgumentNotValidException e, HttpServletRequest request){
        BindingResult bindingResult = e.getBindingResult();
        HashMap<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ResponseError(LocalDateTime.now(), request.getServletPath(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Campos não válidos", errors));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseError> entityNotFound(NoSuchElementException ex, HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ResponseError(LocalDateTime.now(), request.getServletPath(), HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseError> badCredentials(BadCredentialsException e, HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ResponseError(LocalDateTime.now(), request.getServletPath(), HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
    }

    @ExceptionHandler(UsuarioIsNotOwner.class)
    public ResponseEntity<ResponseError> usuarioNaoTemPermissaoSobreORecurso(UsuarioIsNotOwner e, HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ResponseError(LocalDateTime.now(), request.getServletPath(), HttpStatus.FORBIDDEN.value(), e.getMessage()));
    }

}
