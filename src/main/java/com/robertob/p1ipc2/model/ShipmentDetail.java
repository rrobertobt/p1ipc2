package com.robertob.p1ipc2.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ShipmentDetail {
    public ShipmentDetail(int shipment_id, int product_code, int quantity, Double cost) {
        this.shipment_id = shipment_id;
        this.product_code = product_code;
        this.quantity = quantity;
        this.cost = cost;
    }

    public ShipmentDetail(int id, int shipment_id, int product_code, int quantity, Double cost) {
        this.id = id;
        this.shipment_id = shipment_id;
        this.product_code = product_code;
        this.quantity = quantity;
        this.cost = cost;
    }

    private int id;
    private int shipment_id;
    private int product_code;
    private int quantity;
    private Double cost;
}
