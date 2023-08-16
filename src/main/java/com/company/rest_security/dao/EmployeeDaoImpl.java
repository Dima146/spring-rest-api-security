package com.company.rest_security.dao;

import com.company.rest_security.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    private final EntityManager entityManager;

    private static final String FIND_ALL = "SELECT e from Employee e";
    private static final String FIND_BY_ALL_DATA = "from Employee where lower(firstName) like :firstName " +
            "and lower(lastName) like :lastName and lower(email) like :email";

    private static final String FIND_BY_FIRST_OR_LAST_NAME = "from Employee where lower(firstName) " +
            "like :name or lower(lastName) like :name";

    @Autowired
    public EmployeeDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Employee> findEmployeeById(Long id) {
        return Optional.ofNullable(entityManager.find(Employee.class, id));
    }

    @Override
    public List<Employee> findAllEmployees() {
        return entityManager.createQuery(FIND_ALL, Employee.class)
                .getResultList();
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        entityManager.persist(employee);
        return employee;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return entityManager.merge(employee);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        Employee employee = entityManager.find(Employee.class, id);
        entityManager.remove(employee);

    }

    @Override
    public Optional<Employee> findEmployeeByAllData(Employee employee) {

        TypedQuery<Employee> query = entityManager.createQuery(FIND_BY_ALL_DATA, Employee.class)

                        .setParameter("firstName", employee.getFirstName().toLowerCase() + "%")
                        .setParameter("lastName", employee.getLastName().toLowerCase() + "%")
                        .setParameter("email", employee.getEmail().toLowerCase() + "%");

        return query.getResultList().stream().findFirst();
    }

    @Override
    public List<Employee> findByFirstOrLastName(String name) {

        TypedQuery<Employee> query = entityManager.createQuery(FIND_BY_FIRST_OR_LAST_NAME, Employee.class)
                .setParameter("name", name.toLowerCase() + "%");

        return query.getResultList();

    }
}