package com.tensei.tasks.service.impl;

import com.tensei.tasks.domain.entity.User;
import com.tensei.tasks.exception.FailedAuthenticationException;
import com.tensei.tasks.exception.ResourceNotFoundException;
import com.tensei.tasks.exception.UserAlreadyExistException;
import com.tensei.tasks.repository.UserRepository;
import com.tensei.tasks.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        // TODO optimize
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistException("User with such username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistException("User with such email already exists");
        }

        return userRepository.save(user);
    }

    @Override
    public User fetchByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with such username not found"));
    }

    @Override
    public User fetchByCredentials(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new FailedAuthenticationException("Bad credentials"));
    }

    @Override
    public UserDetailsService getUserDetailsService() {
        return this::fetchByUsername;
    }
}
