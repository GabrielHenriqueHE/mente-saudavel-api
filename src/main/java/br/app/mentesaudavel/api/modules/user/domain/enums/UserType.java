package br.app.mentesaudavel.api.modules.user.domain.enums;

import lombok.Getter;

@Getter
public enum UserType {
    PATIENT("PATIENT"),
    PSYCHOLOGIST("PSYCHOLOGIST");

    private final String value;

    UserType(String value) {
        this.value = value;
    }
}
