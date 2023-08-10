package com.company.rest_security.dao;

import com.company.rest_security.entity.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeDao {

    Optional<Employee> findEmployeeById(Long id);

    List<Employee> findAllEmployees();

    Employee saveEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    void deleteEmployeeById(Long id);

    Optional<Employee> findEmployeeByAllData(Employee employee);
}
