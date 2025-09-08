package com.iremayvaz.services;

import com.iremayvaz.model.dto.DtoEmployee;
import com.iremayvaz.model.dto.DtoEmployeeIU;

import java.util.List;

public interface EmployeeService {
    public List<DtoEmployee> filterEmployee(String column, String content);

    public DtoEmployee updateEmployeeInfos(DtoEmployeeIU updatedEmployee);

    public DtoEmployee getEmployeeInfo(String email);

    public void deleteEmployee(String email);
}
