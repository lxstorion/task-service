package com.tensei.tasks.domain.dto.tasks;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record TaskUpdateRequest(
        @NotBlank(message = "Title cannot be blank")
        String title,

        @Size(max = 250)
        String description,

        boolean done
) {
}
