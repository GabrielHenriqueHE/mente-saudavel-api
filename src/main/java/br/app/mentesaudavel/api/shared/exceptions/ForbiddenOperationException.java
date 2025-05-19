package br.app.mentesaudavel.api.shared.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class ForbiddenOperationException extends BaseApplicationException {
    public ForbiddenOperationException(String message, Map<String, String[]> errors) {
        super(message, HttpStatus.FORBIDDEN, errors);
    }
}
