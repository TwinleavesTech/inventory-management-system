package com.twinleaves.ims.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class InventoryStockInfo {
    @NotEmpty
    private String inventoryId;
    private Double quantityCases;
    private Integer quantityUnits;
    private Double volume;
    private String volumeUOM;
    private Double weight;
    private String weightUOM;
}
