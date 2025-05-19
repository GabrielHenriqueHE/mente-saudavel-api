package br.app.mentesaudavel.api.shared.handlers;

import br.app.mentesaudavel.api.shared.dto.ApplicationResponseDTO;
import br.app.mentesaudavel.api.shared.dto.ErrorDetailsDTO;
import br.app.mentesaudavel.api.shared.exceptions.BaseApplicationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final HttpServletRequest request;

    private OffsetDateTime generateTimestamp() {
        return OffsetDateTime.now(ZoneOffset.of("-03:00"));
    }

    @ExceptionHandler(BaseApplicationException.class)
    public ResponseEntity<ApplicationResponseDTO<ErrorDetailsDTO>> handleApplicationException(BaseApplicationException ex) {
        ErrorDetailsDTO errors = ErrorDetailsDTO.builder()
                .method(request.getMethod())
                .path(request.getRequestURI())
                .errors(ex.getErrors())
                .timestamp(this.generateTimestamp())
                .build();


        ApplicationResponseDTO<ErrorDetailsDTO> response = ApplicationResponseDTO
                .<ErrorDetailsDTO>builder()
                .status(ex.getStatus().value())
                .message(ex.getMessage())
                .details(errors)
                .build();

        return ResponseEntity.status(ex.getStatus().value()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApplicationResponseDTO<ErrorDetailsDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
         Map<String, String[]> errorsMap = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ))
                 .entrySet()
                 .stream()
                 .collect(Collectors.toMap(
                         Map.Entry::getKey,
                         entry -> entry.getValue().toArray(new String[0])
                 ));

        ErrorDetailsDTO errorDetails = ErrorDetailsDTO
                .builder()
                .method(request.getMethod())
                .path(request.getRequestURI())
                .errors(errorsMap)
                .timestamp(this.generateTimestamp())
                .build();

        ApplicationResponseDTO<ErrorDetailsDTO> response = ApplicationResponseDTO
                .<ErrorDetailsDTO>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("A validation error occurred during operation.")
                .details(errorDetails)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
