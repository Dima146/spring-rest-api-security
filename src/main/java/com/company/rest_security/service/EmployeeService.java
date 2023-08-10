package com.company.rest_security.service;

import com.company.rest_security.entity.Employee;
import java.util.List;

public interface EmployeeService {

    Employee addEmployee(Employee employee);

    List<Employee> findAll();

    Employee findById(long id);

    Employee update(Employee employee, Long id);

    void deleteById(long id);

}
