package com.tensei.tasks.domain.dto.auth;

import jakarta.annotation.Nullable;
import lombok.Builder;

@Builder
public record UserRegisterRequest(
        String username,
        String password,
        @Nullable String email
) {
}
