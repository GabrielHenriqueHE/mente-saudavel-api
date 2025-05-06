package com.mentesaudavel.mentesaudavel.core.enums;

import lombok.Getter;

@Getter
public enum UserStatusEnum {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String status;

    UserStatusEnum(String status) {
        this.status = status;
    }
}
