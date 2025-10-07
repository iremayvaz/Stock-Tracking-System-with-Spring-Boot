package com.iremayvaz.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "Ürün güncelleme isteği yapılır ve ürün bilgisi alınır.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoProductInsert {
    // IU : Insert - Update
    // REQUEST işlemlerinde kullanılır.
    // @PostMapping veya @PutMapping işlemlerinde kullanılır
    // Validation'lar bu sınıfta yapılır.

    // BARKOD NUMARASI
    @Pattern(regexp = "^[1-9]\\d{12}$", message = "Barkod numarası !")
    @NotBlank(message = "Barkod numarası boş olamaz!")
    private String barcode;

    // KATEGORİ
    @NotBlank(message = "Ürün kategorisi boş olamaz!")
    private String category;

    // ÜRÜN İSMİ
    @NotBlank(message = "Ürünün adını giriniz!")
    private String productName;

    // ÜRÜN RENGİ
    private String color;

    // ÜRÜN BEDENİ
    private String size;

    // ÜRÜN FİYATI
    @NotNull(message = "Ürünün fiyatını giriniz!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Fiyat 0'dan büyük olmalı!")
    private BigDecimal price;

    // ÜRÜN ADEDİ
    @NotNull(message = "Ürünün adedini giriniz!")
    @Min(value = 0, message = "Stok miktarı 0 veya pozitif olmalı!")
    private Integer stockQuantity;

    // ÜRÜN AÇIKLAMASI
    private String explanation;
}
