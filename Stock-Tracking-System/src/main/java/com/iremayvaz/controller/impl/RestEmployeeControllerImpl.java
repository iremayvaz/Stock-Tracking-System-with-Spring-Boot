package com.iremayvaz.controller.impl;

import com.iremayvaz.controller.RestEmployeeController;
import com.iremayvaz.model.dto.DtoEmployee;
import com.iremayvaz.model.dto.DtoEmployeeIU;
import com.iremayvaz.services.impl.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class RestEmployeeControllerImpl implements RestEmployeeController {

    private final EmployeeServiceImpl employeeService;

    @Override
    @PutMapping("/filter")
    public List<DtoEmployee> filterEmployee(String column, String content) {
        return employeeService.filterEmployee(column, content);
    }

    @Override
    @PutMapping("/information")
    public DtoEmployee getEmployeeInfo(String email) { // Giriş yapan kullanıcı bir yerde tutulmalı ki oradan
        return employeeService.getEmployeeInfo(email);
    }

    @Override
    @PostMapping("/update")
    public DtoEmployee updateEmployeeInfos(DtoEmployeeIU updatedEmployee) {
        return employeeService.updateEmployeeInfos(updatedEmployee);
    }
}
