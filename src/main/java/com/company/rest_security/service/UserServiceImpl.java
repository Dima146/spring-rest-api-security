package com.company.rest_security.service;

import com.company.rest_security.dao.RoleDao;
import com.company.rest_security.dao.UserDao;
import com.company.rest_security.entity.Role;
import com.company.rest_security.entity.User;
import com.company.rest_security.exception.DuplicateEntityException;
import com.company.rest_security.exception.NoSuchEntityException;
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

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userDao.findUserByUsername(username);
        if (user.isPresent()) {
            return user.get();

        } else {
            throw new NoSuchEntityException("User with this username was not found - " + username);
        }

    }

    @Override
    public User saveUser(User user) {
        String username = user.getUsername();
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