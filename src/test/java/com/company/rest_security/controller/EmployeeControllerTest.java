package com.company.rest_security.controller;

import com.company.rest_security.Application;
import com.company.rest_security.entity.Employee;
import com.company.rest_security.service.impl.EmployeeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Application.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeServiceImpl employeeService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void EmployeeController_FindAllEmployees_ReturnAllEmployees() throws Exception {

        Employee employee1 = new Employee("John", "Doe", "john@mail.com");
        Employee employee2 = new Employee("James", "Doe", "james@mail.com");

        when(employeeService.findAll()).thenReturn(List.of(employee1, employee2));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

        verify(employeeService, times(1)).findAll();

    }

    @Test
    public void EmployeeController_SaveEmployee_ReturnEmployee() throws Exception {

        Employee employee = new Employee("John", "Doe", "john@mail.com");
        employee.setId(1L);

        when(employeeService.addEmployee(Mockito.any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/employees")
                        .content(objectMapper.writeValueAsString(employee))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.email", is("john@mail.com")));

        verify(employeeService, times(1)).addEmployee(any(Employee.class));

    }

    @Test
    public void EmployeeController_FindById_ReturnEmployee() throws Exception {

        Employee employee = new Employee("John", "Doe", "john@mail.com");
        employee.setId(1L);
        when(employeeService.findById(1L)).thenReturn(employee);

        mockMvc.perform(get("/employees/{id}", 1)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.email", is("john@mail.com")));

        verify(employeeService, times(1)).findById(1L);

    }

    @Test
    public void EmployeeController_Update_ReturnUpdated() throws Exception {

        Employee employee = new Employee("John", "Doe", "john@mail.com");
        employee.setId(1L);

        when(employeeService.update(Mockito.any(Employee.class), Mockito.any(Long.class)))
                .thenReturn(employee);

        employee.setFirstName("James");

        mockMvc.perform(put("/employees/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("James")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.email", is("john@mail.com")));

        verify(employeeService, times(1)).update(any(Employee.class), any(Long.class));

    }

    @Test
    public void EmployeeController_DeleteEmployee_SuccessfullyDeleted() throws Exception {

        mockMvc.perform(delete("/employees/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(employeeService, times(1)).deleteById(1L);

    }

    @Test
    public void EmployeeController_FindByFirstOrLastName_ReturnEmployees() throws Exception {

        Employee employee1 = new Employee("John", "Doe", "john@mail.com");
        Employee employee2 = new Employee("Johnny", "Doe", "johnny@mail.com");
        String name = "John";

        when(employeeService.findByFirstOrLastName(Mockito.any(String.class))).thenReturn(List.of(employee1, employee2));

        mockMvc.perform(get("/employees/{name}/", name))
//                        .param("name", name))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

        verify(employeeService, times(1)).findByFirstOrLastName(name);

    }
}