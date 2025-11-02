package com.tensei.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tensei.tasks.domain.dto.auth.JwtAuthenticationResponse;
import com.tensei.tasks.domain.dto.auth.UserRegisterRequest;
import com.tensei.tasks.service.impl.JwtAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenServiceExampleRunner implements CommandLineRunner {

    private final JwtAuthService jwtAuthService;

    @Override
    public void run(String... args) throws Exception {
        UserRegisterRequest request = UserRegisterRequest.builder()
                .username("another")
                .password("another-pass")
                .email("example@gmail.com")
                .build();

        JwtAuthenticationResponse response = jwtAuthService.register(request);
        System.out.print("Response: ");
        new ObjectMapper().writeValue(System.out, response);
    }
}
