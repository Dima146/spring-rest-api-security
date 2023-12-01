package com.company.restsecurity.service;

import com.company.restsecurity.dao.EmployeeDao;
import com.company.restsecurity.entity.Employee;
import com.company.restsecurity.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeDao employeeDao;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    public void EmployeeService_Save_ReturnSaved() {
        Employee employee = new Employee("John", "Doe", "john@mail.com");
        when(employeeDao.saveEmployee(Mockito.any(Employee.class))).thenReturn(employee);

        Employee savedEmployee = employeeService.addEmployee(employee);
        assertThat(savedEmployee).isNotNull();

    }

    @Test
    public void EmployeeService_FindAllEmployees_ReturnAllEmployees() {
        Employee employee1 = new Employee("John", "Doe", "john@mail.com");
        Employee employee2 = new Employee("James", "Doe", "james@mail.com");

        when(employeeDao.findAllEmployees()).thenReturn(List.of(employee1, employee2));

        List<Employee> employees = employeeService.findAll();
        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(2);
    }

    @Test
    public void EmployeeService_FindById_ReturnEmployee() {
        Employee employee = new Employee("John", "Doe", "john@mail.com");
        when(employeeDao.findEmployeeById(Mockito.any(Long.class))).thenReturn(Optional.of(employee));

        Employee result = employeeService.findById(1L);
        assertThat(result).isNotNull();
    }

    @Test
    public void EmployeeService_UpdateEmployee_ReturnUpdated() {
        Employee employee = new Employee("John", "Doe", "john@mail.com");
        when(employeeDao.findEmployeeById(Mockito.any(Long.class))).thenReturn(Optional.of(employee));
        when(employeeDao.updateEmployee(Mockito.any(Employee.class))).thenReturn(employee);

        employee.setFirstName("James");
        Employee result = employeeService.update(employee, 1L);
        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("James");
    }

    @Test
    public void EmployeeService_DeleteEmployee_SuccessfullyDeleted() {
        Employee employee = new Employee("John", "Doe", "john@mail.com");
        when(employeeDao.findEmployeeById(1L)).thenReturn(Optional.of(employee));

        employeeService.deleteById(1L);
        verify(employeeDao, times(1)).deleteEmployeeById(1L);
    }

    @Test
    public void EmployeeService_FindByFirstOrLastName_ReturnEmployees() {
        Employee employee1 = new Employee("John", "Doe", "john@mail.com");
        Employee employee2 = new Employee("Johnny", "Doe", "johnny@mail.com");
        when(employeeDao.findByFirstOrLastName(Mockito.any(String.class))).thenReturn(List.of(employee1, employee2));

        List<Employee> result = employeeService.findByFirstOrLastName("John");
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
    }
}