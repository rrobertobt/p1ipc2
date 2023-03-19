package com.robertob.p1ipc2.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter @Setter
public class Order {
    public Order(int id, LocalDateTime createdDate, String status, String description, int store_code, int user_code) {
        this.id = id;
        this.createdDate = createdDate;
        this.status = status;
        this.description = description;
        this.store_code = store_code;
        this.store_user_code = user_code;
    }

    public Order(String status, String description, int store_code, int user_code) {
        this.status = status;
        this.description = description;
        this.store_code = store_code;
        this.store_user_code = user_code;
    }

    private int id;
    private LocalDateTime createdDate;
    private String status;
    private String description;
    private int store_code;
    private int store_user_code;
}
