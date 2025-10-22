package com.tensei.tasks.domain.dto.auth;

import lombok.Builder;

@Builder
public record UserLoginResponse(
        Long id,
        String username,
        String email
) {
}
