package com.iremayvaz.services;

import com.iremayvaz.model.dto.DtoEmployee;
import com.iremayvaz.model.dto.DtoUserUpdate;
import com.iremayvaz.model.dto.DtoUserInsert;

import java.util.List;

public interface EmployeeService {
    public List<DtoEmployee> filterEmployee(String column, String content);

    public DtoEmployee updateEmployeeInfos(Long id, DtoUserUpdate updateUserRequest);

    public DtoUserUpdate getEmployeeInfo(Long id);

    public void deleteEmployee(Long id);
}
