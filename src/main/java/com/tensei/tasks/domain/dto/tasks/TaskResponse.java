package com.tensei.tasks.domain.dto.tasks;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record TaskResponse(
        @JsonProperty("id") Long id,
        @JsonProperty("title") String title,
        @JsonProperty("description") String description,
        @JsonProperty("done") boolean done,
        @JsonProperty("owner") TaskOwnerResponse owner
) {
}
