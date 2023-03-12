package com.robertob.p1ipc2.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IncidentDetail {
    public IncidentDetail(int incidentId, int productCode, int quantity, String description) {
        this.incidentId = incidentId;
        this.productCode = productCode;
        this.quantity = quantity;
        this.description = description;
    }

    private int incidentId;
    private int productCode;
    private int quantity;
    private String description;
}
