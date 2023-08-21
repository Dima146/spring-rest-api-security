package com.company.rest_security.dto.converter.impl;

import com.company.rest_security.dto.UserDto;
import com.company.rest_security.dto.converter.DtoConverter;
import com.company.rest_security.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter implements DtoConverter<User, UserDto> {

    @Override
    public User convertToEntity(UserDto dto) {
        User entity = new User();

        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setEnabled(true);

        return entity;
    }

    @Override
    public UserDto convertToDto(User entity) {
        UserDto dto = new UserDto();

        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());

        return dto;
    }
}
