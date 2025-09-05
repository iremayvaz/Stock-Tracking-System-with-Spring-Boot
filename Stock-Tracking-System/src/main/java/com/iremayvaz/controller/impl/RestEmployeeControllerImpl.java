package com.iremayvaz.controller.impl;

import com.iremayvaz.controller.RestEmployeeController;
import com.iremayvaz.model.dto.DtoEmployee;
import com.iremayvaz.model.dto.DtoEmployeeIU;
import com.iremayvaz.services.impl.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class RestEmployeeControllerImpl implements RestEmployeeController {

    /*
    * EMPLOYEE,
    ACCOUNTANT,
    SECRETARY,
    BOSS,
    CONSULTANT,
    VISITOR,
    AUTHORIZED
    *
    * EMPLOYEE_LIST,
    EMPLOYEE_UPDATE,
    EMPLOYEE_DELETE,
    EMPLOYEE_ADD,*/


    private final EmployeeServiceImpl employeeService;

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_LIST') and (hasRole('ACCOUNTANT') or hasRole('SECRETARY') or hasRole('BOSS') or" +
                                                     "hasRole('CONSULTANT') or hasRole('AUTHORIZED'))")
    @PutMapping("/filter")
    public List<DtoEmployee> filterEmployee(String column, String content) {
        return employeeService.filterEmployee(column, content);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_LIST') and (hasRole('ACCOUNTANT') or hasRole('SECRETARY') or hasRole('BOSS') or" +
                                                    "hasRole('CONSULTANT') or hasRole('AUTHORIZED'))")
    @PutMapping("/information")
    public DtoEmployee getEmployeeInfo(String email) { // Giriş yapan kullanıcı bir yerde tutulmalı ki oradan emaili al koy
        return employeeService.getEmployeeInfo(email);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_UPDATE') and (hasRole('SECRETARY') or hasRole('BOSS') or hasRole('AUTHORIZED'))")
    @PostMapping("/update")
    public DtoEmployee updateEmployeeInfos(DtoEmployeeIU updatedEmployee) {
        return employeeService.updateEmployeeInfos(updatedEmployee);
    }
}
