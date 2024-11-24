package com.procrastination.domain.user;


import jakarta.validation.constraints.NotBlank;

public record UserRegisterRequest(
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String phoneNumber
) {
}
