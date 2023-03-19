package com.robertob.p1ipc2.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WarehouseUser {
    public WarehouseUser(int code, String name, String username, String password, String email, boolean active) {
        this.code = code;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public WarehouseUser(int code, String name, String username, String email) {
        this.code = code;
        this.name = name;
        this.username = username;
        this.email = email;
    }

    private int code;
    private String name;
    private String username;
    private String password;
    private String email;
    private boolean active;
}
