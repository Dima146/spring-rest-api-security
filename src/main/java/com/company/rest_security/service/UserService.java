package com.company.rest_security.service;

import com.company.rest_security.entity.User;

public interface UserService {

    User findByUsername(String username);

    User saveUser(User user);
}
