package com.tensei.tasks.service.impl;

import com.tensei.tasks.domain.entity.Role;
import com.tensei.tasks.domain.entity.User;
import com.tensei.tasks.exception.UnknownPermissionException;
import com.tensei.tasks.exception.UserAlreadyExistException;
import com.tensei.tasks.repository.RoleRepository;
import com.tensei.tasks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        String[] roles = user.getRoles().stream().map(Role::getName).toArray(String[]::new);
        System.out.println(String.join(", ", roles));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(roles)
                .build();
    }

    public User register(User user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(u -> {
            throw new UserAlreadyExistException("User already exists");
        });
        Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new UnknownPermissionException("Unrecognized role"));
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        return userRepository.save(user);
    }
}
