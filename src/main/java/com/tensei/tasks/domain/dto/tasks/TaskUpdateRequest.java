package com.tensei.tasks.domain.dto.tasks;

import lombok.Builder;

@Builder
public record TaskUpdateRequest(
        String title,
        String description,
        boolean done
) {
}
