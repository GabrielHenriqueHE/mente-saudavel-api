package br.app.mentesaudavel.api.shared.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class ResourceNotFoundException extends BaseApplicationException {
    public ResourceNotFoundException(String message, Map<String, String[]> errors) {
        super(message, HttpStatus.NOT_FOUND, errors);
    }
}
