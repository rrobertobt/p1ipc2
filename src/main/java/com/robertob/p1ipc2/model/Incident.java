package com.robertob.p1ipc2.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter @Setter
public class Incident {
    public Incident(int id, LocalDateTime createdDate, String status, int store_code, int user_code) {
        this.id = id;
        this.createdDate = createdDate;
        this.status = status;
        this.store_code = store_code;
        this.user_code = user_code;
    }

    public Incident(LocalDateTime createdDate, String status, int store_code, int user_code) {
        this.createdDate = createdDate;
        this.status = status;
        this.store_code = store_code;
        this.user_code = user_code;
    }

    private int id;
    private LocalDateTime createdDate;
    private String status;
    private int store_code;
    private int user_code;
}
