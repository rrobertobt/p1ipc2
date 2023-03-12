package com.robertob.p1ipc2.model;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ReturningDetail {
    public ReturningDetail(int returningId, int productCode, int quantity, String description, Double cost) {
        this.returningId = returningId;
        this.productCode = productCode;
        this.quantity = quantity;
        this.description = description;
        this.cost = cost;
    }

    private int returningId;
    private int productCode;
    private int quantity;
    private String description;
    private Double cost;
}
