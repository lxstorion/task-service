package com.tensei.tasks.controller;

import com.tensei.tasks.domain.dto.auth.JwtAuthenticationResponse;
import com.tensei.tasks.domain.dto.auth.UserLoginRequest;
import com.tensei.tasks.domain.dto.auth.UserRegisterRequest;
import com.tensei.tasks.service.impl.JwtAuthService;
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

    private final JwtAuthService jwtAuthService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtAuthenticationResponse> register(@RequestBody UserRegisterRequest registerRequest) {

        JwtAuthenticationResponse response = jwtAuthService.register(registerRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody UserLoginRequest loginRequest) {
        JwtAuthenticationResponse response = jwtAuthService.login(loginRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
