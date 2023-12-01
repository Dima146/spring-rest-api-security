package com.company.restsecurity.service.impl;

import com.company.restsecurity.dao.EmployeeDao;
import com.company.restsecurity.entity.Employee;
import com.company.restsecurity.exception.DuplicateEntityException;
import com.company.restsecurity.exception.NoSuchEntityException;
import com.company.restsecurity.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    public EmployeeServiceImpl( EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public Employee addEmployee(Employee employee) {
        LOGGER.info("Create a new employee with the first and last name: {} {}",
                employee.getFirstName(), employee.getLastName());
        Optional<Employee> result = employeeDao.findEmployeeByAllData(employee);
        if (result.isPresent()) {
            throw new DuplicateEntityException("Employee with these data already exists - "
                                                + employee.getFirstName() + " " +
                                                employee.getLastName() + " " +
                                                employee.getEmail());
        }
        return employeeDao.saveEmployee(employee);
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = employeeDao.findAllEmployees();
        if (employees.isEmpty()) {
            throw new NoSuchEntityException("Employees were not found");
        }
        return employees;
    }

    @Override
    public Employee findById(long id) {
        Optional<Employee> result = employeeDao.findEmployeeById(id);
        if (result.isEmpty()) {
            throw new NoSuchEntityException("Employee was not found by id - " + id);
        }
        return result.get();
    }

    @Override
    public Employee update(Employee employee, Long id) {
        LOGGER.info("Update an employee with id: {}", employee.getId());
        Optional<Employee> result = employeeDao.findEmployeeById(id);
        if (result.isPresent()) {
            return employeeDao.updateEmployee(employee);
        } else {
            throw new NoSuchEntityException("Employee with id - " + id + " does not exist in the database");
        }
    }

    @Override
    public void deleteById(long id) {
        LOGGER.info("Delete an employee with id: {}", id);
        Optional<Employee> employee = employeeDao.findEmployeeById(id);
        if (employee.isEmpty()) {
            throw new NoSuchEntityException("Employee was not found by id - " + id);
        }
        employeeDao.deleteEmployeeById(id);
    }

    @Override
    public List<Employee> findByFirstOrLastName(String name) {
        List<Employee> employees = employeeDao.findByFirstOrLastName(name);
        if (employees.isEmpty()) {
            throw new NoSuchEntityException("Employees were not found by this name - " + name);
        }
        return employees;
    }
}