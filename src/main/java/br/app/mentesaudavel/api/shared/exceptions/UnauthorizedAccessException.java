package br.app.mentesaudavel.api.shared.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class UnauthorizedAccessException extends BaseApplicationException {
    public UnauthorizedAccessException(String message, Map<String, String[]> errors) {
        super(message, HttpStatus.UNAUTHORIZED, errors);
    }
}
