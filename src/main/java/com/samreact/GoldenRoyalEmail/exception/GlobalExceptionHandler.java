package com.samreact.GoldenRoyalEmail.exception;

import com.samreact.GoldenRoyalEmail.dto.response.ErrorResponse;
import com.samreact.GoldenRoyalEmail.data.enums.ResponseStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException ex) {
        String message = Objects.requireNonNullElse(ex.getMessage(), "Entity not found");
        log.error("Entity not found: {}", message);
        ErrorResponse response = new ErrorResponse(ResponseStatus.ERROR, message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {
        String message = Objects.requireNonNullElse(ex.getMessage(), "Invalid argument");
        log.error("Illegal argument: {}", message);
        ErrorResponse response = new ErrorResponse(ResponseStatus.ERROR, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder("Validation failed: ");
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = Objects.requireNonNullElse(error.getDefaultMessage(), "Invalid value");
            errorMessage.append(fieldName).append(" - ").append(message).append("; ");
        });

        String finalMessage = errorMessage.toString();
        ErrorResponse response = new ErrorResponse(ResponseStatus.ERROR, finalMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        String message = Objects.requireNonNullElse(ex.getMessage(), "An unexpected error occurred");
        log.error("Unexpected error occurred: {}", message, ex);
        ErrorResponse response = new ErrorResponse(ResponseStatus.ERROR, "An unexpected error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

