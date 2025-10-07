package com.iremayvaz.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "Ürün oluşturma başarılıysa ürün bilgileri döndürülür.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoProduct {
    // GET
    // RESPONSE işlemlerinde kullanılır
    // @GetMapping
    // Kullanıcıya sistemden dönen veriyi ilettir
    // Bu sebeple validasyon gerekmez.

    private Long id;
    private String barcode;
    private String productName;
    private String color;
    private String size;
    private BigDecimal price;
    private Integer stockQuantity;

}
