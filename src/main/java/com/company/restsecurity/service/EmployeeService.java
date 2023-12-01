package com.company.restsecurity.service;

import com.company.restsecurity.entity.Employee;
import java.util.List;

public interface EmployeeService {

    Employee addEmployee(Employee employee);
    List<Employee> findAll();
    Employee findById(long id);
    Employee update(Employee employee, Long id);
    void deleteById(long id);
    List<Employee> findByFirstOrLastName(String name);

}
