package com.twinleaves.ims.model;

import lombok.Data;

@Data
public class InventoryStockInfo {
    private String inventoryId;
    private Double quantityCases;
    private Integer quantityUnits;
    private Double volume;
    private String volumeUOM;
    private Double weight;
    private String weightUOM;
}
