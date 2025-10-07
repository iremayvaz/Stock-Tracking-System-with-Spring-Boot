package com.iremayvaz.model.dto;

import com.iremayvaz.model.entity.enums.Gender;
import com.iremayvaz.model.entity.enums.RoleName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Çalışan oluşturma isteği yapılır.")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoUserInsert {
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
    @NotBlank(message = "Soyisim girilmeli!")
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

    @NotNull(message = "Unvan girilmeli!")
    private RoleName position;

    @NotNull(message = "Cinsiyet boş bırakılamaz!")
    private Gender gender;
}
