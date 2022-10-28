package com.solinftec.dev8api.resource.exceptions;

import org.springframework.boot.context.properties.bind.validation.BindValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StdErrorBody> dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new StdErrorBody(
                        Instant.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Valor duplicado",
                        ex.getMessage(),
                        req.getRequestURI()

                ));
    }

    @ExceptionHandler(BindValidationException.class)
    public ResponseEntity<StdErrorBody> bindValidation(RuntimeException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new StdErrorBody(
                        Instant.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Erro de vínculo",
                        ex.getMessage(),
                        req.getRequestURI()

                ));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StdErrorBody> constraintViolation(RuntimeException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new StdErrorBody(
                        Instant.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Erro de validação",
                        ex.getMessage(),
                        req.getRequestURI()

                ));
    }

}
