package com.tensei.tasks.service.impl;

import com.tensei.tasks.domain.dto.auth.JwtAuthenticationResponse;
import com.tensei.tasks.domain.dto.auth.UserLoginRequest;
import com.tensei.tasks.domain.dto.auth.UserRegisterRequest;
import com.tensei.tasks.domain.entity.User;
import com.tensei.tasks.domain.entity.enums.Role;
import com.tensei.tasks.mapper.AuthMapper;
import com.tensei.tasks.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAuthService {

    private final JwtService jwtService;
    private final UserService userService;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Register user in application
     *
     * @param userRegisterRequest user data request
     * @return JwtAuthenticationResponse contains JWT token
     */
    public JwtAuthenticationResponse register(UserRegisterRequest userRegisterRequest) {
        User user = authMapper.fromUserRegisterRequest(userRegisterRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        userService.create(user);

        return new JwtAuthenticationResponse(jwtService.generateToken(user));
    }

    /**
     * Login user in application
     *
     * @param userLoginRequest user data request
     * @return JwtAuthenticationResponse contains JWT token
     */
    public JwtAuthenticationResponse login(UserLoginRequest userLoginRequest) {
        return null;
    }
}
