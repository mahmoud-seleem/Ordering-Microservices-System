package com.example.orders.core.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)  // Excludes null values
public class OrderDetailsDto {
    private Long orderId;
    private List<ProductDetailsDto> productsDetails;
    private Date orderDate;
    private Double totalPrice;


    public OrderDetailsDto() {
    }

    public OrderDetailsDto(Long orderId, List<ProductDetailsDto> productsDetails, Date orderDate, Double totalPrice) {
        this.orderId = orderId;
        this.productsDetails = productsDetails;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<ProductDetailsDto> getProductsDetails() {
        return productsDetails;
    }

    public void setProductsDetails(List<ProductDetailsDto> productsDetails) {
        this.productsDetails = productsDetails;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
