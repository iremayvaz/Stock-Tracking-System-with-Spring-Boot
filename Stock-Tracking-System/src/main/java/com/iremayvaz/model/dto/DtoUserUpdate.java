package com.iremayvaz.model.dto;

import com.iremayvaz.model.entity.enums.Gender;
import com.iremayvaz.model.entity.enums.RoleName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Çalışan güncelleme isteği ve çalışan bilgisi alma isteği yapılır.")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoUserUpdate {
    private Long id;
    private String tck_no;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private String email;
    private String password;
    private RoleName position;
    private Gender gender;
}

