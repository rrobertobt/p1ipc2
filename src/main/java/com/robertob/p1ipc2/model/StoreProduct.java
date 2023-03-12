package com.robertob.p1ipc2.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoreProduct {
    public StoreProduct(int storeCode, int productCode) {
        this.storeCode = storeCode;
        this.productCode = productCode;
    }

    private int storeCode;
    private int productCode;
}
