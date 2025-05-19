package br.app.mentesaudavel.api.shared.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class InternalServerErrorException extends BaseApplicationException {
    public InternalServerErrorException(String message, Map<String, String[]> errors) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, errors);
    }
}
