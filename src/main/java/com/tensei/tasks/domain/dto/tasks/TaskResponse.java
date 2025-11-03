package com.tensei.tasks.domain.dto.tasks;

import lombok.Builder;

@Builder
public record TaskResponse(
        Long id,
        String title,
        String description,
        boolean done
) {
}
