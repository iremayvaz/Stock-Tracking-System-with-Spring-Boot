package com.iremayvaz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoProduct {
    // GET
    // RESPONSE işlemlerinde kullanılır
    // @GetMapping
    // Kullanıcıya sistemden dönen veriyi ilettir
    // Bu sebeple validasyon gerekmez.

    private String barcode;
    private String productName;
    private String color;
    private String size;
    private String price;
    private String number;

}
