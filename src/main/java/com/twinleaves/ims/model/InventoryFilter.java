package com.twinleaves.ims.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InventoryFilter {

    private List<String> inventoryId;
    private List<String> productId;
    private List<String> category;
    private List<String> locationId;
    private List<String> locationType;
    private List<String> sourceInventory;
    private String inwardedOnDate;
    private List<String> inwardedBy;
    private String expirationDate;
    private List<String> itemCode;
    private List<String> itemName;
    private List<String> department;
    private List<String> brand;
    private List<String> shortName;
    private List<String> merchandise;
    private List<String> itemType;
    private List<String> stockType;
    private List<String> batchNo;
    private List<String> style;
}
