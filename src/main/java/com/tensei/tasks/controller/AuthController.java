package com.tensei.tasks.controller;

import com.tensei.tasks.domain.dto.auth.UserLoginRequest;
import com.tensei.tasks.domain.dto.auth.UserLoginResponse;
import com.tensei.tasks.domain.dto.auth.UserRegisterRequest;
import com.tensei.tasks.domain.dto.auth.UserRegisterResponse;
import com.tensei.tasks.service.impl.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserLoginResponse> login(
            @RequestBody UserLoginRequest userLoginRequest,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        UserLoginResponse userLoginResponse = authService.login(
                userLoginRequest.username(),
                userLoginRequest.password(),
                request, response
        );

        return ResponseEntity.ok(userLoginResponse);
    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRegisterResponse> register(
            @RequestBody UserRegisterRequest userRegisterRequest,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        UserRegisterResponse userRegisterResponse = authService.register(
                userRegisterRequest.username(),
                userRegisterRequest.password(),
                userRegisterRequest.email(),
                request, response
        );

        return new ResponseEntity<>(userRegisterResponse, HttpStatus.CREATED);
    }
}
