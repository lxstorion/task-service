package com.tensei.tasks;

import com.tensei.tasks.domain.entity.User;
import com.tensei.tasks.domain.entity.enums.Role;
import com.tensei.tasks.service.UserService;
import com.tensei.tasks.service.impl.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenServiceExampleRunner implements CommandLineRunner {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {

        User user = User.builder()
                .username("example")
                .password("example-password")
                .email("example@example.com")
                .role(Role.ROLE_USER)
                .build();

        userService.create(user);

        String generatedToken = jwtService.generateToken(user);
        System.out.println("Generated token: " + generatedToken);

    }
}
