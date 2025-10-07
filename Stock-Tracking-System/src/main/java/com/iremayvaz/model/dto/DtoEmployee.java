package com.iremayvaz.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Kullanıcı oluşturulurken oluşan çalışan bilgileri döndürülür.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoEmployee {
    // GET
    // RESPONSE işlemlerinde kullanılır
    // @GetMapping
    // Kullanıcıya sistemden dönen veriyi ilettir
    // Bu sebeple validasyon gerekmez.

    private Long id;
    private String firstName;
    private String lastName;
    private String roleName; // sadece rol adı gelecek
    private String email;

}
