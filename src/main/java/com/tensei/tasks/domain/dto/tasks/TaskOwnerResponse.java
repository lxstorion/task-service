package com.tensei.tasks.domain.dto.tasks;

import com.tensei.tasks.domain.entity.enums.Role;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TaskOwnerResponse(
        Long id,
        String username,
        String password,
        String email,
        LocalDateTime registerAt,
        Role role
) {
}
