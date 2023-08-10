package com.company.rest_security.controller;

import com.company.rest_security.dto.EmployeeDto;
import com.company.rest_security.dto.converter.DtoConverter;
import com.company.rest_security.entity.Employee;
import com.company.rest_security.hateoas.EmployeeHateoasProvider;
import com.company.rest_security.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DtoConverter<Employee, EmployeeDto> employeeDtoConverter;
    private final EmployeeHateoasProvider hateoasProvider;

    @Autowired
    public EmployeeController(EmployeeService employeeService, DtoConverter<Employee,
                              EmployeeDto> employeeDtoConverter, EmployeeHateoasProvider hateoasProvider) {

        this.employeeService = employeeService;
        this.employeeDtoConverter = employeeDtoConverter;
        this.hateoasProvider = hateoasProvider;
    }

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto addEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee addedEmployee = employeeService.addEmployee(employeeDtoConverter.convertToEntity(employeeDto));
        EmployeeDto employee = employeeDtoConverter.convertToDto(addedEmployee);
        hateoasProvider.addLinks(employee);

        return employee;
    }

    @GetMapping("/employees")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> findAll() {
        List<Employee> employeesList = employeeService.findAll();

        List<EmployeeDto> employees =
                employeesList.stream()
                        .map(employeeDtoConverter::convertToDto)
                        .peek(hateoasProvider::addLinks)
                        .toList();

        return employees;
    }

    @GetMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto findById(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        EmployeeDto employeeDto = employeeDtoConverter.convertToDto(employee);
        hateoasProvider.addLinks(employeeDto);

        return employeeDto;
    }

    @PutMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable Long id) {
        Employee updatedEmployee = employeeService.update(employeeDtoConverter.convertToEntity(employeeDto), id);
        EmployeeDto employee = employeeDtoConverter.convertToDto(updatedEmployee);
        hateoasProvider.addLinks(employee);

        return employee;
    }

    @DeleteMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Employee was deleted - " + id);
    }
}