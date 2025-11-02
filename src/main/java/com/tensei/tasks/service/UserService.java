package com.tensei.tasks.service;

import com.tensei.tasks.domain.entity.User;

public interface UserService {
    User create(User user);
    User fetchByUsername(String username);
}
