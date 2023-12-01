package com.company.restsecurity.service.impl;

import com.company.restsecurity.dao.RoleDao;
import com.company.restsecurity.dao.UserDao;
import com.company.restsecurity.entity.Role;
import com.company.restsecurity.entity.User;
import com.company.restsecurity.exception.DuplicateEntityException;
import com.company.restsecurity.exception.NoSuchEntityException;
import com.company.restsecurity.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;

    private final BCryptPasswordEncoder passwordEncoder;
    private final static String DEFAULT_ROLE = "ROLE_EMPLOYEE";
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        LOGGER.info("Find a user with the username {}", username);
        Optional<User> user = userDao.findUserByUsername(username);
        if (user.isEmpty()) {
            throw new NoSuchEntityException("User with this username was not found - " + username);
        }
        return user.get();
    }

    @Override
    public User saveUser(User user) {
        String username = user.getUsername();
        LOGGER.info("Save a new user with the username {}", username);
        Optional<User> userInDB = userDao.findUserByUsername(username);
        if (userInDB.isPresent()) {
            throw new DuplicateEntityException("User with this username already exists - " + username);
        }
        Optional<Role> defaultRole = roleDao.findRoleByName(DEFAULT_ROLE);
        if (defaultRole.isEmpty()) {
            throw new NoSuchEntityException("Role was not found");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(defaultRole.get()));
        return userDao.saveUser(user);
    }
}