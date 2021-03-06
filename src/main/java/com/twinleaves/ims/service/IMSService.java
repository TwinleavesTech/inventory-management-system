package com.twinleaves.ims.service;

import com.twinleaves.ims.model.Inventory;
import com.twinleaves.ims.model.InventoryFilter;
import com.twinleaves.ims.model.InventoryFilterData;
import com.twinleaves.ims.model.InventoryStockInfo;

import java.util.List;

public interface IMSService {

    Inventory addInventory(final Inventory inventory);

    Inventory getInventoryById(final String inventoryId);

    void updateConsumedInventoryStock(final InventoryStockInfo consumedInventoryStockInfo);

    InventoryFilterData getInventoryListFilterData(final InventoryFilter inventoryFilter);

    void deleteInventory(final String inventoryId);
}
