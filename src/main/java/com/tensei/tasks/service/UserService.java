package com.tensei.tasks.service;

import com.tensei.tasks.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    User create(User user);
    User fetchByUsername(String username);
    User fetchByCredentials(String username, String password);
    UserDetailsService getUserDetailsService();
}
