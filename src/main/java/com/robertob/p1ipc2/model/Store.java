package com.robertob.p1ipc2.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class Store {
    public Store(int code, String name, String address, boolean supervised, int warehouseUserCode) {
        this.code = code;
        this.name = name;
        this.address = address;
        this.supervised = supervised;
        this.warehouseUserCode = warehouseUserCode;
    }

    private int code;
    private String name;
    private String address;
    private boolean supervised;
    private int warehouseUserCode;
}
