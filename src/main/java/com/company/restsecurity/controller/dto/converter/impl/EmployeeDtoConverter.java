package com.company.restsecurity.controller.dto.converter.impl;

import com.company.restsecurity.controller.dto.converter.DtoConverter;
import com.company.restsecurity.controller.dto.EmployeeDto;
import com.company.restsecurity.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDtoConverter implements DtoConverter<Employee, EmployeeDto> {

    @Override
    public Employee convertToEntity(EmployeeDto dto) {
        Employee entity = new Employee();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        return entity;
    }

    @Override
    public EmployeeDto convertToDto(Employee entity) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        return dto;
    }
}