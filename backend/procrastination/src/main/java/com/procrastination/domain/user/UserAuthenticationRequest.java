package com.procrastination.domain.user;

import jakarta.validation.constraints.NotBlank;

public record UserAuthenticationRequest(
        @NotBlank String email,
        @NotBlank String password
) {
}
