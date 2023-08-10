package com.company.rest_security.dao;

import com.company.rest_security.entity.User;
import java.util.Optional;

public interface UserDao {

    Optional<User> findUserByUsername(String username);

    User saveUser(User user);
}
