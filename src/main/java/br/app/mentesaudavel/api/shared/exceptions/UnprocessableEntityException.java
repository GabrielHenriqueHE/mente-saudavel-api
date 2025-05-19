package br.app.mentesaudavel.api.shared.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class UnprocessableEntityException extends BaseApplicationException {
    public UnprocessableEntityException(String message, Map<String, String[]> errors) {
        super(message, HttpStatus.UNPROCESSABLE_ENTITY, errors);
    }
}
