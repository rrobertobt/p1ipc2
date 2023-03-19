package com.robertob.p1ipc2.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderDetail {
    public OrderDetail(int id, int order_id, int product_code, int quantity, Double cost) {
        this.id = id;
        this.order_id = order_id;
        this.product_code = product_code;
        this.quantity = quantity;
        this.cost = cost;
    }

    public OrderDetail(int order_id, int product_code, int quantity, Double cost) {
        this.order_id = order_id;
        this.product_code = product_code;
        this.quantity = quantity;
        this.cost = cost;
    }

    private int id;
    private int order_id;
    private int product_code;
    private int quantity;
    private Double cost;
}
