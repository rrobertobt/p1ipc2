package com.robertob.p1ipc2.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter @Getter
public class Shipment {
    public Shipment(int id, LocalDateTime departureDate, LocalDateTime receivedDate, String status, int store_code, int warehouse_user_code) {
        this.id = id;
        this.departureDate = departureDate;
        this.receivedDate = receivedDate;
        this.status = status;
        this.store_code = store_code;
        this.warehouse_user_code = warehouse_user_code;
    }

    public Shipment(LocalDateTime departureDate, LocalDateTime receivedDate, String status, int store_code, int warehouse_user_code) {
        this.departureDate = departureDate;
        this.receivedDate = receivedDate;
        this.status = status;
        this.store_code = store_code;
        this.warehouse_user_code = warehouse_user_code;
    }

    private int id;
    private LocalDateTime departureDate;
    private LocalDateTime receivedDate;
    private String status;
    private int store_code;
    private int warehouse_user_code;
}
