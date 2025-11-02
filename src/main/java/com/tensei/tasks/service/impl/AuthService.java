package com.tensei.tasks.service.impl;

import com.tensei.tasks.domain.dto.auth.UserLoginResponse;
import com.tensei.tasks.domain.dto.auth.UserRegisterResponse;
import com.tensei.tasks.domain.entity.User;
import com.tensei.tasks.exception.FailedAuthenticationException;
import com.tensei.tasks.exception.UserAlreadyExistException;
import com.tensei.tasks.mapper.AuthMapper;
import com.tensei.tasks.repository.UserRepository;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final SecurityContextRepository securityContextRepository;
    private final UserRepository userRepository;
    private final AuthMapper authMapper;

    public UserLoginResponse login(
            String username,
            String password,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        authenticate(username, password, request, response);

        User user = userRepository.findByUsername(username).orElseThrow(() -> new FailedAuthenticationException("Bad credentials"));

        return authMapper.toAuthResponseDto(user);

    }

    public UserRegisterResponse register(
            String username,
            String password,
            @Nullable String email,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        userRepository.findByUsername(username).ifPresent(u -> {
            throw new UserAlreadyExistException("Username already reserved");
        });

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();

        userRepository.save(user);

        authenticate(username, password, request, response);

        return authMapper.toUserRegisterResponse(user);
    }

    private void authenticate(
            String username,
            String password,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        securityContextRepository.saveContext(securityContext, request, response);
    }

}
