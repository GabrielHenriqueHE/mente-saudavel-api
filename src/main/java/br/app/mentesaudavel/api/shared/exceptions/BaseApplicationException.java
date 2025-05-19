package br.app.mentesaudavel.api.shared.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class BaseApplicationException extends RuntimeException {

    protected final HttpStatus status;
    protected final Map<String, String[]> errors;

    public BaseApplicationException(
            String message,
            HttpStatus status,
            Map<String, String[]> errors
    ) {
        super(message);

        this.status = status;
        this.errors = errors;
    }
}
