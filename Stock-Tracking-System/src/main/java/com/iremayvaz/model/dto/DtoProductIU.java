package com.iremayvaz.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoProductIU {
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
    @NotBlank(message = "Ürünün fiyatını giriniz!")
    private String price;

    // ÜRÜN ADEDİ
    @NotBlank(message = "Ürünün adedini giriniz!")
    private String number;

    // ÜRÜN AÇIKLAMASI
    private String explanation;
}
