package com.example.inventory.dto;

import lombok.Getter;
import lombok.Setter;
//@Getter
//@Setter
public class ProductRequestDto {

    private String name;

    private Float price;

    private Integer quantity;

//    private final Integer inventory_id=1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
