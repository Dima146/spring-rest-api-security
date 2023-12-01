package com.company.restsecurity.dao;

import com.company.restsecurity.entity.User;
import java.util.Optional;

public interface UserDao {

    Optional<User> findUserByUsername(String username);
    User saveUser(User user);
}
