package com.company.restsecurity.service;

import com.company.restsecurity.entity.User;

public interface UserService {

    User findByUsername(String username);
    User saveUser(User user);
}
