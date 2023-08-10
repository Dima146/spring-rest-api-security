package com.company.rest_security.service;

import com.company.rest_security.dao.EmployeeDao;
import com.company.rest_security.entity.Employee;
import com.company.rest_security.exception.DuplicateEntityException;
import com.company.rest_security.exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;

    @Autowired
    public EmployeeServiceImpl( EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public Employee addEmployee(Employee employee) {
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

        Employee employee;
        if (result.isPresent()) {
            employee = result.get();
        }
        else {
            throw new NoSuchEntityException("Employee was not found by id - " + id);
        }
        return employee;
    }

    @Override
    public Employee update(Employee employee, Long id) {
        Optional<Employee> result = employeeDao.findEmployeeById(id);

        if (result.isPresent()) {
            return employeeDao.updateEmployee(employee);

        } else {
            throw new NoSuchEntityException("Employee does not exist in the database - " + id);
        }
    }

    @Override
    public void deleteById(long id) throws NoSuchEntityException {
        Optional<Employee> employee = employeeDao.findEmployeeById(id);
        if (employee.isEmpty()) {
            throw new NoSuchEntityException("Employee was not found by id - " + id);

        }
        employeeDao.deleteEmployeeById(id);
    }
}