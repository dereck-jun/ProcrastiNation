package com.procrastination.domain.user;

import com.procrastination.domain.entity.UserEntity;

import java.time.LocalDateTime;

public record User(
        String email,
        String nickname,
        String phoneNumber,
        UserPosition userPosition,
        UserRole role,
        LocalDateTime createdDateTime,
        LocalDateTime updatedDateTime
) {
    public static User from(UserEntity entity) {
        return new User(
                entity.getEmail(),
                entity.getNickname(),
                entity.getPhoneNumber(),
                entity.getUserPosition(),
                entity.getRole(),
                entity.getCreatedDateTime(),
                entity.getUpdatedDateTime()
        );
    }
}
