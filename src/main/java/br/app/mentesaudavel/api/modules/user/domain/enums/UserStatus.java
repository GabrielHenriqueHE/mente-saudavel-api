package br.app.mentesaudavel.api.modules.user.domain.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String value;

    UserStatus(String value) {
        this.value = value;
    }
}
