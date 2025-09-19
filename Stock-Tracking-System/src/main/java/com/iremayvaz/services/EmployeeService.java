package com.iremayvaz.services;

import com.iremayvaz.model.dto.DtoEmployee;
import com.iremayvaz.model.dto.DtoEmployeeDetail;
import com.iremayvaz.model.dto.DtoUserIU;

import java.util.List;

public interface EmployeeService {
    public List<DtoEmployee> filterEmployee(String column, String content);

    public DtoEmployee updateEmployeeInfos(Long id, DtoUserIU updateUserRequest);

    public DtoEmployeeDetail getEmployeeInfo(Long id);

    public void deleteEmployee(Long id);
}
