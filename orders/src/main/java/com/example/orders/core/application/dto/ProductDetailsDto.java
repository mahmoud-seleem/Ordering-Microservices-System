package com.example.orders.core.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)  // Excludes null values
public class ProductDetailsDto {
    private String productName;
    private Integer productId;
    private Integer quantity;
    private Double priceOfUnit;

    public ProductDetailsDto() {
    }

    public ProductDetailsDto(String productName, Integer productId, Integer quantity, Double priceOfUnit) {
        this.productName = productName;
        this.productId = productId;
        this.quantity = quantity;
        this.priceOfUnit = priceOfUnit;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPriceOfUnit() {
        return priceOfUnit;
    }

    public void setPriceOfUnit(Double priceOfUnit) {
        this.priceOfUnit = priceOfUnit;
    }
}
