package com.tensei.tasks.domain.dto.auth;

import lombok.Builder;

@Builder
public record UserLoginRequest(
        String username,
        String password
) {
}