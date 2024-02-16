package com.company.restsecurity.controller;

import com.company.restsecurity.controller.dto.UserDto;
import com.company.restsecurity.controller.dto.converter.DtoConverter;
import com.company.restsecurity.entity.User;
import com.company.restsecurity.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final DtoConverter<User, UserDto> userDtoConverter;

    @Autowired
    public UserController(UserService userService, DtoConverter<User, UserDto> userDtoConverter) {
        this.userService = userService;
        this.userDtoConverter = userDtoConverter;
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto registerUser(@RequestBody @Valid UserDto userDto) {
        User savedUser = userService.saveUser(userDtoConverter.convertToEntity(userDto));
        return userDtoConverter.convertToDto(savedUser);
    }
}