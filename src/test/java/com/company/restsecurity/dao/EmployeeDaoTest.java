package com.company.restsecurity.dao;

import com.company.restsecurity.Application;
import com.company.restsecurity.dao.impl.EmployeeDaoImpl;
import com.company.restsecurity.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ContextConfiguration(classes = { EmployeeDaoImpl.class, Application.class})
public class EmployeeDaoTest {

    @Autowired
    private EmployeeDaoImpl employeeDao;


    @Test
    public void EmployeeDao_Save_ReturnEmployee() {

        Employee employee = new Employee("John", "Doe", "john@mail.com");
        Employee result = employeeDao.saveEmployee(employee);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);

    }

    @Test
    public void EmployeeDao_FindAll_ReturnAllEmployees() {

        Employee employee1 = new Employee("John", "Doe", "john@mail.com");
        Employee employee2 = new Employee("James", "Doe", "james@mail.com");
        employeeDao.saveEmployee(employee1);
        employeeDao.saveEmployee(employee2);

        List<Employee> employees = employeeDao.findAllEmployees();
        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(2);
    }

    @Test
    public void EmployeeDao_Update_ReturnUpdatedEmployee() {

        Employee employee = new Employee("John", "Doe", "john@mail.com");
        employeeDao.saveEmployee(employee);

        employee.setFirstName("Peter");
        Employee result = employeeDao.updateEmployee(employee);

        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("Peter");
    }

    @Test
    public void EmployeeDao_DeleteById_SuccessfullyDeleted() {

        Employee employee = new Employee("John", "Doe", "john@mail.com");
        employeeDao.saveEmployee(employee);

        employeeDao.deleteEmployeeById(1L);
        List<Employee> employees = employeeDao.findAllEmployees();
        assertThat(employees).isEmpty();
    }

    @Test
    public void EmployeeDao_FindByAllData_ReturnEmployee() {

        Employee employee = new Employee("John", "Doe", "john@mail.com");
        employeeDao.saveEmployee(employee);

        Optional<Employee> result = employeeDao.findEmployeeByAllData(employee);
        assertThat(result).isNotEmpty();
        assertThat(result.get().getFirstName()).isEqualTo("John");
    }

    @Test
    public void EmployeeDao_FindByFirstOrLastName_ReturnEmployees() {

        Employee employee = new Employee("John", "Doe", "john@mail.com");
        Employee employee1 = new Employee("Johnny", "Doe", "johnny@mail.com");
        employeeDao.saveEmployee(employee);
        employeeDao.saveEmployee(employee1);

        List<Employee> result = employeeDao.findByFirstOrLastName("John");
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(2);
    }
}
