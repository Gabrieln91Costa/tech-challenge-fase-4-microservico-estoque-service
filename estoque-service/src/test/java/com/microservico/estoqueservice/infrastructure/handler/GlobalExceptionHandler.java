package com.microservico.estoqueservice.infrastructure.handler;

import com.microservico.estoqueservice.domain.exception.EstoqueNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EstoqueNaoEncontradoException.class)
    public ResponseEntity<String> handleEstoqueNaoEncontrado(EstoqueNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Pode adicionar outros handlers de exceção aqui
}
