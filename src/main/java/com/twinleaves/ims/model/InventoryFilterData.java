package com.twinleaves.ims.model;

import lombok.Data;

import java.util.List;

@Data
public class InventoryFilterData {
    private List<Inventory> inventoryList;
}
