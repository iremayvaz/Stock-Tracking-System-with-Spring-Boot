package com.iremayvaz.controller;

import com.iremayvaz.model.dto.DtoEmployee;
import com.iremayvaz.model.dto.DtoUserIU;
import com.iremayvaz.model.userDetails.AppUserDetails;
import com.iremayvaz.services.impl.EmployeeServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class RestEmployeeControllerImpl{

    private final EmployeeServiceImpl employeeService;

    @PreAuthorize( "hasAuthority('EMPLOYEE_LIST') and hasAnyRole('ACCOUNTANT', 'SECRETARY', 'BOSS', 'CONSULTANT', 'AUTHORIZED')" )
    @GetMapping("/filter")
    public List<DtoEmployee> filterEmployee(@RequestParam(required = false) String column,
                                            @RequestParam(required = false) String content) {
        return employeeService.filterEmployee(column, content);
    }


    @PreAuthorize( "hasAuthority('ME') and hasAnyRole('ACCOUNTANT', 'SECRETARY', 'BOSS', 'CONSULTANT', 'AUTHORIZED', 'EMPLOYEE', 'VISITOR')" )
    @GetMapping("/me")
    public DtoEmployee me(@AuthenticationPrincipal AppUserDetails me) { // Giriş yapan kullanıcı bir yerde tutulmalı ki oradan emaili al koy
        return employeeService.getEmployeeInfo(me.getId());
    }

    @PreAuthorize( "hasAuthority('EMPLOYEE_DELETE') and hasAnyRole('SECRETARY', 'BOSS', 'AUTHORIZED')" )
    @DeleteMapping("/delete/{id}")
    public void deleteEmployee(@PathVariable(value = "id") @NotNull Long id) {
        employeeService.deleteEmployee(id);
    }

    @PreAuthorize( "hasAuthority('EMPLOYEE_UPDATE') and hasAnyRole('SECRETARY', 'BOSS', 'AUTHORIZED')" )
    @PutMapping("/update/{id}")
    public DtoEmployee updateEmployeeInfos(@PathVariable(value = "id") @NotNull Long id,
                                           @RequestBody @Valid DtoUserIU updateUserRequest) {
        return employeeService.updateEmployeeInfos(id, updateUserRequest);
    }
}
