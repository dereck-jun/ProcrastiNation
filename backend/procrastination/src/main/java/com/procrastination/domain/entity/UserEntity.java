package com.procrastination.domain.entity;

import com.procrastination.domain.user.UserPosition;
import com.procrastination.domain.user.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column
    @Setter(value = PRIVATE)
    private String email;

    @Column
    @Setter(value = PRIVATE)
    private String password;

    @Column
    @Setter(value = PRIVATE)
    private String nickname;

    @Column
    private Boolean isAdmin;

    @Column
    @Setter(value = PRIVATE)
    private String phoneNumber;

    @Column
    @Enumerated(STRING)
    private UserPosition userPosition;

    @Column
    @Enumerated(STRING)
    private UserRole role;

    @Column
    private LocalDateTime createdDateTime;

    @Column
    private LocalDateTime updatedDateTime;

    public static UserEntity of(String email, String password, String phoneNumber) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setPassword(password);
        userEntity.setNickname("user_"
                + UUID.randomUUID().toString().substring(0, 8));
        userEntity.setPhoneNumber(phoneNumber);
        return userEntity;
    }

    @PrePersist
    public void prePersist() {
        createdDateTime = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedDateTime = LocalDateTime.now();
    }

}
