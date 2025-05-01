package com.mentesaudavel.mentesaudavel.core.exceptions.handlers;

import com.mentesaudavel.mentesaudavel.core.dto.out.ErrorResponseDTO;
import com.mentesaudavel.mentesaudavel.core.exceptions.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleBadRequestException(BadRequestException e) {
        ErrorResponseDTO<Void> errorResponse = new ErrorResponseDTO<>(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleUnprocessableEntityException(UnprocessableEntityException e) {
        ErrorResponseDTO<Void> errorResponse = new ErrorResponseDTO<>(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                e.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleResourceNotFoundException(ResourceNotFoundException e) {
        ErrorResponseDTO<Void> errorResponse = new ErrorResponseDTO<>(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleUnauthorizedAccessException(UnauthorizedAccessException e) {
        ErrorResponseDTO<Void> errorResponse = new ErrorResponseDTO<>(
                HttpStatus.UNAUTHORIZED.value(),
                e.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleInternalServerErrorException(InternalServerErrorException e) {
        ErrorResponseDTO<Void> errorResponse = new ErrorResponseDTO<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponseDTO<Map<String, String>> errorResponse = new ErrorResponseDTO<>(
                HttpStatus.BAD_REQUEST.value(),
                "An error occurred during data valiidation.",
                errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO<Map<String, String>>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponseDTO<Map<String, String>> errorResponse = new ErrorResponseDTO<>(
                HttpStatus.BAD_REQUEST.value(),
                "An error occurred during data validation",
                errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
