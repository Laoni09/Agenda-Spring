package com.example.agenda.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

// Avisa ao Spring: "Eu trato os erros de toda a aplicação"
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    // Avisa ao Spring: "Se alguém lançar uma RuntimeException, chame este método"
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<StandardError> handleRuntimeException(RuntimeException e, HttpServletRequest request) {

        StandardError errorResponse = new StandardError(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Erro de Regra de Negócio",
            e.getMessage(),
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {

        FieldError erro = e.getBindingResult().getFieldError();
        String mensagemValidacao = (erro != null) ? erro.getDefaultMessage() : "Erro de validação";

        StandardError errorResponse = new StandardError(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Erro de Validação",
            mensagemValidacao,
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> handleEntityNotFound(EntityNotFoundException e, HttpServletRequest request) {

        StandardError errorResponse = new StandardError(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "Entidade não encontrada",
            e.getMessage(),
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
