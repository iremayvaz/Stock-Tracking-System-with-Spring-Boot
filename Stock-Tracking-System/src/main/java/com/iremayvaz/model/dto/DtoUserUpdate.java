package com.iremayvaz.model.dto;

import com.iremayvaz.model.entity.enums.Gender;
import com.iremayvaz.model.entity.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoEmployeeDetail {
    private Long id;
    private String tck_no;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private String email;
    private RoleName position;
    private Gender gender;
}

