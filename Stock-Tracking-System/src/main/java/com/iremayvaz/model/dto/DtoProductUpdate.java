package com.iremayvaz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoProductDetail {
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
