package com.reuven.healthcare.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This class provides centralized exception handling across all controllers.
 * It catches specific exceptions and returns meaningful HTTP responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
     * Handles IllegalArgumentException thrown anywhere in the application.
     * Returns a 400 Bad Request response with the exception message.
     *
     * Example use case: validation failures, overlapping appointment logic, etc.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}
