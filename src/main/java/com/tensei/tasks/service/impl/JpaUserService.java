package com.tensei.tasks.service.impl;

import com.tensei.tasks.domain.entity.User;
import com.tensei.tasks.exception.UserAlreadyExistException;
import com.tensei.tasks.repository.UserRepository;
import com.tensei.tasks.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistException("User with such username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistException("User with such email already exists");
        }

        return userRepository.save(user);
    }
}
