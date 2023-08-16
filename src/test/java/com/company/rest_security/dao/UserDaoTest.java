package com.company.rest_security.dao;

import com.company.rest_security.Application;
import com.company.rest_security.dao.impl.UserDaoImpl;
import com.company.rest_security.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ContextConfiguration(classes = { UserDaoImpl.class, Application.class})
public class UserDaoTest {

    @Autowired
    private UserDaoImpl userDao;


    @Test
    public void UserDao_SaveUser_ReturnUser() {

        User user = new User("john", "Qwerty1+", true);
        User result = userDao.saveUser(user);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);

    }

    @Test
    public void UserDao_FindByUsername_ReturnUser() {

        User user = new User("john", "Qwerty1+", true);
        userDao.saveUser(user);

        Optional<User> result = userDao.findUserByUsername("john");
        assertThat(result).isNotEmpty();

    }
}
