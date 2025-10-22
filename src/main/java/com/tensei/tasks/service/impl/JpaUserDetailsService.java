package com.tensei.tasks.service.impl;

import com.tensei.tasks.domain.entity.User;
import com.tensei.tasks.exception.ResourceNotFoundException;
import com.tensei.tasks.exception.UserAlreadyExistException;
import com.tensei.tasks.repository.RoleRepository;
import com.tensei.tasks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Username not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();

    }

    public User register(User user) {

        userRepository.findByUsername(user.getUsername()).ifPresent(u -> {
            throw new UserAlreadyExistException("User already exists");
        });

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);

    }
}
