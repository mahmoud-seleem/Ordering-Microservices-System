package com.example.inventory.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Entity
@Table(name = "Inventory")
//@Getter
//@Setter
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "inventory")
    private List<Product> products;

    @NotNull
    private int maxInvSize;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getMaxInvSize() {
        return maxInvSize;
    }

    public void setMaxInvSize(int maxInvSize) {
        this.maxInvSize = maxInvSize;
    }

}
