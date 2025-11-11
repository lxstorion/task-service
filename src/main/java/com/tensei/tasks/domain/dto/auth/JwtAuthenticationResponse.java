package com.tensei.tasks.domain.dto.auth;

import lombok.Builder;

@Builder
public record JwtAuthenticationResponse(
        String token
) {
}
