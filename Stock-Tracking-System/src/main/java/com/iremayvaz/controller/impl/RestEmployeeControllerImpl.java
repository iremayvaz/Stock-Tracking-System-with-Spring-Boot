package com.iremayvaz.controller.impl;

import com.iremayvaz.controller.RestEmployeeController;
import com.iremayvaz.model.dto.DtoEmployee;
import com.iremayvaz.model.dto.DtoEmployeeIU;
import com.iremayvaz.services.impl.EmployeeServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class RestEmployeeControllerImpl implements RestEmployeeController {

    private final EmployeeServiceImpl employeeService;

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_LIST') and (hasRole('ACCOUNTANT') or hasRole('SECRETARY') or hasRole('BOSS') or" +
                                                     "hasRole('CONSULTANT') or hasRole('AUTHORIZED'))")
    @PutMapping("/filter")
    public List<DtoEmployee> filterEmployee(@PathVariable String column,
                                            @PathVariable String content) {
        return employeeService.filterEmployee(column, content);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_LIST') and (hasRole('ACCOUNTANT') or hasRole('SECRETARY') or hasRole('BOSS') or" +
                                                    "hasRole('CONSULTANT') or hasRole('AUTHORIZED'))")
    @PutMapping("/information")
    public DtoEmployee getEmployeeInfo(@PathVariable String email) { // Giriş yapan kullanıcı bir yerde tutulmalı ki oradan emaili al koy
        return employeeService.getEmployeeInfo(email);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_DELETE') and (hasRole('SECRETARY') or hasRole('BOSS') or hasRole('AUTHORIZED'))")
    @DeleteMapping("/delete")
    public void deleteEmployee(String email) {
        employeeService.deleteEmployee(email);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_UPDATE') and (hasRole('SECRETARY') or hasRole('BOSS') or hasRole('AUTHORIZED'))")
    @PostMapping("/update")
    public DtoEmployee updateEmployeeInfos(@RequestBody @Valid DtoEmployeeIU updatedEmployee) {
        return employeeService.updateEmployeeInfos(updatedEmployee);
    }
}
