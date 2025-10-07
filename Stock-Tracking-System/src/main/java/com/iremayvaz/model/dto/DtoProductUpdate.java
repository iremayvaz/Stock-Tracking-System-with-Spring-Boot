package com.iremayvaz.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "Ürün güncelleme isteği ve ürün bilgisi alma isteği yapılır.")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoProductUpdate {
    private Long id;
    private String barcode;
    private String category;
    private String productName;
    private String color;
    private String size;
    private BigDecimal price;
    private Integer stockQuantity;
    private String explanation;

}
