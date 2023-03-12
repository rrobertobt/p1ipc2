package com.robertob.p1ipc2.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AdminUser {
    public AdminUser(int code, String name, String username, String password) {
        this.code = code;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public AdminUser(int code, String name, String username) {
        this.code = code;
        this.name = name;
        this.username = username;
    }

    private int code;
    private String name;
    private String username;
    private String password;
}
