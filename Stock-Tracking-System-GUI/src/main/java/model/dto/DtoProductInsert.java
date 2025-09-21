package model.dto;

import java.math.BigDecimal;

public class DtoProductInsert {
    private String barcode;
    private String category;
    private String productName;
    private String color;
    private String size;
    private BigDecimal price;
    private Integer stockQuantity;
    private String explanation;

    public DtoProductInsert(){}

    public DtoProductInsert(String barcode,
                            String category,
                            String productName,
                            String color,
                            String size,
                            String price,
                            String stockQuantity,
                            String explanation){
        this.barcode = barcode;
        this.category = category;
        this.productName = productName;
        this.color = color;
        this.size = size;
        this.price = new BigDecimal(price);
        this.stockQuantity = Integer.parseInt(stockQuantity);
        this.explanation = explanation;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
