package com.company.restsecurity.controller;

import com.company.restsecurity.controller.dto.UserCredentialDto;
import com.company.restsecurity.controller.dto.UserDto;
import com.company.restsecurity.controller.dto.converter.DtoConverter;
import com.company.restsecurity.entity.User;
import com.company.restsecurity.security.jwt.JWTAuthProvider;
import com.company.restsecurity.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final DtoConverter<User, UserDto> userDtoConverter;
    private final JWTAuthProvider jwtAuthProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService, DtoConverter<User, UserDto> userDtoConverter,
                          JWTAuthProvider jwtAuthProvider, AuthenticationManager authenticationManager) {

        this.userService = userService;
        this.userDtoConverter = userDtoConverter;
        this.jwtAuthProvider = jwtAuthProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto registerUser(@RequestBody @Valid UserDto userDto) {
        User savedUser = userService.saveUser(userDtoConverter.convertToEntity(userDto));
        return userDtoConverter.convertToDto(savedUser);
    }

    @PostMapping("/authentication")
    @ResponseStatus(HttpStatus.OK)
    public UserCredentialDto authenticateUser(@RequestBody @Valid UserCredentialDto userCredentialDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userCredentialDto.getUsername(), userCredentialDto.getPassword()));
        String token = jwtAuthProvider.generateToken(userCredentialDto.getUsername());
        userCredentialDto.setToken(token);
        return userCredentialDto;
    }
}