package com.procrastination.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {

    ADMIN("ROLE_ADMIN", "관리자"),
    USER("ROLE_USER", "일반 사용자");

    private final String value;
    private final String description;
}
