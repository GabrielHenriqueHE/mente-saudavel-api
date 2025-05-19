package br.app.mentesaudavel.api.shared.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class BadRequestException extends BaseApplicationException {
    public BadRequestException(String message, Map<String, String[]> errors) {
        super(message, HttpStatus.BAD_REQUEST, errors);
    }
}
