package com.company.restsecurity.controller.hateoas;

import com.company.restsecurity.controller.EmployeeController;
import com.company.restsecurity.controller.dto.EmployeeDto;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EmployeeHateoasProvider extends RepresentationModel<EmployeeDto> {
    private static final Class<EmployeeController> CONTROLLER = EmployeeController.class;

    public EmployeeHateoasProvider() {
    }

    public void addLinks(EmployeeDto employeeDto) {

        employeeDto.add(linkTo(methodOn(CONTROLLER)
                .findById(employeeDto.getId())).withSelfRel());
        employeeDto.add(linkTo(methodOn(CONTROLLER)
                .updateEmployee(employeeDto, employeeDto.getId())).withRel("update"));
        employeeDto.add(linkTo(methodOn(CONTROLLER)
                .deleteEmployee(employeeDto.getId())).withRel("delete"));
        employeeDto.add(linkTo(methodOn(CONTROLLER)
                .findAll()).withRel("all employees"));
        employeeDto.add(linkTo(methodOn(CONTROLLER)
                .findByFirstOrLastName(employeeDto.getFirstName())).withRel("by first/last name"));
        employeeDto.add(linkTo(methodOn(CONTROLLER)
                .addEmployee(employeeDto)).withRel("create"));
    }
}
 