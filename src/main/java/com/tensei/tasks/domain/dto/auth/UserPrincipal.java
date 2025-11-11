package com.tensei.tasks.domain.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Builder
public record UserPrincipal(
        @JsonProperty("username") String username,
        @JsonProperty("password") String password,
        @JsonProperty("authorities") Collection<? extends GrantedAuthority> authorities
) {
}
