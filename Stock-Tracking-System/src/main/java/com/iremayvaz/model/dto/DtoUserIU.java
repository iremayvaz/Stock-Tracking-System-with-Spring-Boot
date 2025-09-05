package com.iremayvaz.model.dto;

import com.iremayvaz.model.entity.enums.Gender;
import com.iremayvaz.model.entity.enums.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoUserIU {
    // IU : Insert - Update
    // REQUEST işlemlerinde kullanılır.
    // @PostMapping veya @PutMapping işlemlerinde kullanılır
    // Validation'lar bu sınıfta yapılır.

    @NotBlank(message = "TC Kimlik No girilmeli!")
    @Pattern(regexp = "^[1-9]\\d{10}$", message = "TC Kimlik No 11 haneli ve 0 ile başlayamaz!")
    private String tck_no;

    @Size(min=2, max=30, message = "İsim en az 2 en fazla 30 karakter uzunluğunda olabilir!")
    @NotBlank(message = "İsim girilmeli!")
    private String firstName;

    @Size(min=2, max=30, message = "Soyisim en az 2 en fazla 30 karakter uzunluğunda olabilir!")
    @NotBlank(message = "Email girilmeli!")
    private String lastName;

    @Pattern(regexp = "^5\\d{9}$", message = "Telefon numarasını başında 0 olmadan giriniz!")
    private String phoneNum;

    @NotBlank(message = "Email girilmeli!")
    @Email(message="Geçerli bir e-posta girin")
    private String email;

    @NotBlank
    @Size(min = 8, max = 64)
    @Pattern(regexp="^(?=.*[A-Z])(?=.*\\d).+$",
            message="En az bir büyük harf ve bir rakam içermeli")
    private String password;

    @NotBlank(message = "Unvan girilmeli!")
    private RoleName position;

    @NotBlank(message = "Cinsiyet boş bırakılamaz!")
    private Gender gender;
}
