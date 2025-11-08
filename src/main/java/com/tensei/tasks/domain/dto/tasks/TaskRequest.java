package com.tensei.tasks.domain.dto.tasks;

import lombok.Builder;

@Builder
public record TaskRequest(
        String title,
        String description
) {
}
