package com.company.rest_security.controller;

import com.company.rest_security.Application;
import com.company.rest_security.dto.UserDto;
import com.company.rest_security.entity.User;
import com.company.rest_security.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void UserController_RegisterUser_ReturnedRegisteredUser() throws Exception {

        UserDto userDto = new UserDto();
        userDto.setUsername("john");
        userDto.setPassword("Qwerty1+");
        userDto.setMatchingPassword("Qwerty1+");

        User user = new User("john", "Qwerty1+", true);
        when(userService.saveUser(Mockito.any(User.class))).thenReturn(user);

        mockMvc.perform(post("/user/register")
                .content(objectMapper.writeValueAsString(userDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is("john")));

        verify(userService, times(1)).saveUser(any(User.class));

    }
}