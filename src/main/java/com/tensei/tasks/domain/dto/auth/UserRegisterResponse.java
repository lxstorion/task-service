package com.tensei.tasks.domain.dto.auth;

import jakarta.annotation.Nullable;
import lombok.Builder;

@Builder
public record UserRegisterResponse(
        Long id,
        String username,
        @Nullable String email
) {
}
