package com.company.restsecurity.service;

import com.company.restsecurity.dao.RoleDao;
import com.company.restsecurity.dao.UserDao;
import com.company.restsecurity.entity.Role;
import com.company.restsecurity.entity.User;
import com.company.restsecurity.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;
    @Mock
    private RoleDao roleDao;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;
    private final static String DEFAULT_ROLE = "ROLE_EMPLOYEE";


    @Test
    public void UserService_FindByUsername_ReturnUser() {
        User user = new User("john", "Qwerty1+", true);
        when(userDao.findUserByUsername("john")).thenReturn(Optional.of(user));

        User result = userService.findByUsername("john");
        assertThat(result).isNotNull();
    }

    @Test
    public void UserService_SaveUser_Return_User() {
        User user = new User("john", "Qwerty1+", true);
        Role role = new Role(DEFAULT_ROLE);

        when(roleDao.findRoleByName(DEFAULT_ROLE)).thenReturn(Optional.of(role));
        when(userDao.saveUser(Mockito.any(User.class))).thenReturn(user);

        User result = userService.saveUser(user);
        assertThat(result).isNotNull();
    }
}