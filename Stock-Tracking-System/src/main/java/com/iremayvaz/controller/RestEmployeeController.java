package com.iremayvaz.controller;

import com.iremayvaz.model.dto.DtoEmployee;
import com.iremayvaz.model.dto.DtoEmployeeIU;
import com.iremayvaz.model.userDetails.AppUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

public interface RestEmployeeController {

    public List<DtoEmployee> filterEmployee(String column, String content);

    public DtoEmployee updateEmployeeInfos(String email, DtoEmployeeIU updatedEmployee);

    public DtoEmployee me(AppUserDetails me);

    public void deleteEmployee(String email); // USER'LA İLİŞKİLİ EMPLOYEE TABLOSU DİREKT SİLİNİR Mİ KONTROL ET!!!
}
