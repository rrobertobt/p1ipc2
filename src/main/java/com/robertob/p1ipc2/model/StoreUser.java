package com.robertob.p1ipc2.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoreUser {
    public StoreUser(int code, String name, String username, String password, String email, int store_code) {
        this.code = code;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.store_code = store_code;
    }

    public StoreUser(int code, String name, String username, String email, boolean active, int store_code) {
        this.code = code;
        this.name = name;
        this.username = username;
        this.email = email;
        this.active = active;
        this.store_code = store_code;
    }

    private int code;
    private String name;
    private String username;
    private String password;
    private String email;
    private boolean active;
    private int store_code;

}
