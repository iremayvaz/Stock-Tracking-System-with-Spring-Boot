package com.iremayvaz.controller.impl;

import com.iremayvaz.controller.RestEmployeeController;
import com.iremayvaz.model.dto.DtoEmployee;
import com.iremayvaz.model.dto.DtoEmployeeIU;
import com.iremayvaz.model.userDetails.AppUserDetails;
import com.iremayvaz.services.impl.EmployeeServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class RestEmployeeControllerImpl implements RestEmployeeController {

    private final EmployeeServiceImpl employeeService;

    @Override
    @PreAuthorize( "hasAuthority('EMPLOYEE_LIST') and hasRole('ACCOUNTANT', 'SECRETARY', 'BOSS', 'CONSULTANT', 'AUTHORIZED')" )
    @GetMapping("/filter")
    public List<DtoEmployee> filterEmployee(@RequestParam(required = false) String column,
                                            @RequestParam(required = false) String content) {
        return employeeService.filterEmployee(column, content);
    }

    @Override
    @PreAuthorize( "hasAuthority('ME') and hasRole('ACCOUNTANT', 'SECRETARY', 'BOSS', 'CONSULTANT', 'AUTHORIZED', 'EMPLOYEE', 'VISITOR')" )
    @GetMapping("/me")
    public DtoEmployee me(@AuthenticationPrincipal AppUserDetails me) { // Giriş yapan kullanıcı bir yerde tutulmalı ki oradan emaili al koy
        return employeeService.getEmployeeInfo(me.getUsername());
    }

    @Override
    @PreAuthorize( "hasAuthority('EMPLOYEE_DELETE') and hasRole('SECRETARY', 'BOSS', 'AUTHORIZED')" )
    @DeleteMapping("/delete/{email}")
    public void deleteEmployee(@PathVariable(value = "email") String email) {
        employeeService.deleteEmployee(email);
    }

    @Override
    @PreAuthorize( "hasAuthority('EMPLOYEE_UPDATE') and hasRole('SECRETARY', 'BOSS', 'AUTHORIZED')" )
    @PutMapping("/update/{email}")
    public DtoEmployee updateEmployeeInfos(@PathVariable(value = "email") String email,
                                           @RequestBody @Valid DtoEmployeeIU updatedEmployee) {
        return employeeService.updateEmployeeInfos(email, updatedEmployee);
    }
}
