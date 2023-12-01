package com.company.restsecurity.controller;

import com.company.restsecurity.controller.dto.EmployeeDto;
import com.company.restsecurity.controller.dto.converter.DtoConverter;
import com.company.restsecurity.entity.Employee;
import com.company.restsecurity.controller.hateoas.EmployeeHateoasProvider;
import com.company.restsecurity.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/employees")
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto addEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        Employee addedEmployee = employeeService.addEmployee(employeeDtoConverter.convertToEntity(employeeDto));
        EmployeeDto employee = employeeDtoConverter.convertToDto(addedEmployee);
        hateoasProvider.addLinks(employee);

        return employee;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> findAll() {
        List<Employee> employeesList = employeeService.findAll();
        return employeesList.stream()
                .map(employeeDtoConverter::convertToDto)
                .peek(hateoasProvider::addLinks)
                .toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto findById(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        EmployeeDto employeeDto = employeeDtoConverter.convertToDto(employee);
        hateoasProvider.addLinks(employeeDto);

        return employeeDto;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto updateEmployee(@RequestBody @Valid EmployeeDto employeeDto, @PathVariable Long id) {
        Employee updatedEmployee = employeeService.update(employeeDtoConverter.convertToEntity(employeeDto), id);
        EmployeeDto employee = employeeDtoConverter.convertToDto(updatedEmployee);
        hateoasProvider.addLinks(employee);

        return employee;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Employee with id " + id + " was deleted");
    }

    @GetMapping("/{name}/")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> findByFirstOrLastName(@PathVariable String name) {
        List<Employee> employeesList = employeeService.findByFirstOrLastName(name);
        return employeesList.stream()
                .map(employeeDtoConverter::convertToDto)
                .peek(hateoasProvider::addLinks)
                .toList();
    }
}