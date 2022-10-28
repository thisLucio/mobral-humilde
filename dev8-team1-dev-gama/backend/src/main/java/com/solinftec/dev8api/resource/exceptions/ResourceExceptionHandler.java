package com.solinftec.dev8api.resource.exceptions;

import com.solinftec.dev8api.service.exceptions.DatabaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<StdErrorBody> resourceException(DatabaseException ex, HttpServletRequest req) {
        return (ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new StdErrorBody(
                        Instant.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "An error occurred",
                        ex.getMessage(),
                        req.getRequestURI()
                )
        ));
    }
}
