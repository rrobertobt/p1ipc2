package com.robertob.p1ipc2.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Product {
    public Product(int code, String name, Double cost, Double price, int stock) {
        this.code = code;
        this.name = name;
        this.cost = cost;
        this.price = price;
        this.stock = stock;
    }

    private int code;
    private String name;
    private Double cost;
    private Double price;
    private int stock;
}
