package com.tensei.tasks.service.impl;

import com.tensei.tasks.domain.dto.auth.JwtAuthenticationResponse;
import com.tensei.tasks.domain.dto.auth.UserLoginRequest;
import com.tensei.tasks.domain.dto.auth.UserRegisterRequest;
import com.tensei.tasks.domain.entity.User;
import com.tensei.tasks.domain.entity.enums.Role;
import com.tensei.tasks.mapper.AuthMapper;
import com.tensei.tasks.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAuthService {

    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Register user in application
     *
     * @param userRegisterRequest user data request
     * @return JwtAuthenticationResponse contains JWT token
     */
    public JwtAuthenticationResponse register(UserRegisterRequest userRegisterRequest) {

        User user = User.builder()
                .username(userRegisterRequest.username())
                .password(passwordEncoder.encode(userRegisterRequest.password()))
                .email(userRegisterRequest.email())
                .role(Role.ROLE_USER)
                .build();

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
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequest.username(),
                        userLoginRequest.password()
                )
        );

        var user = userService.getUserDetailsService().loadUserByUsername(userLoginRequest.username());

        return new JwtAuthenticationResponse(jwtService.generateToken(user));
    }
}
