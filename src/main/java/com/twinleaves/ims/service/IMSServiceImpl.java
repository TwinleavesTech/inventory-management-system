package com.twinleaves.ims.service;

import com.twinleaves.ims.controller.IMSController;
import com.twinleaves.ims.dao.InventoryDAOService;
import com.twinleaves.ims.entity.InventoryEntity;
import com.twinleaves.ims.mappers.InventoryMapper;
import com.twinleaves.ims.model.Inventory;
import com.twinleaves.ims.model.InventoryFilter;
import com.twinleaves.ims.model.InventoryFilterData;
import com.twinleaves.ims.model.InventoryStockInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IMSServiceImpl implements IMSService {

    private static final Logger log = LoggerFactory.getLogger(IMSController.class);

    private InventoryDAOService inventoryDAOService;

    private InventoryMapper inventoryMapper;

    @Autowired
    public IMSServiceImpl(InventoryDAOService inventoryDAOService, InventoryMapper inventoryMapper) {
        this.inventoryDAOService = inventoryDAOService;
        this.inventoryMapper = inventoryMapper;
    }

    /**
     * Adds Inventory details to database.
     * @param inventory Inventory
     * @return Inventory
     */
    @Override
    public Inventory addInventory(Inventory inventory) {
        InventoryEntity inventoryEntity = inventoryDAOService.saveInventory(inventoryMapper.inventoryToInventoryEntity(inventory));
        log.info("inventoryEntity::: " + inventoryEntity);
        Inventory detail = inventoryMapper.inventoryEntityToInventory(inventoryEntity);
        return detail;
    }

    /**
     * Fetches Inventory details of given Inventory.
     * @param inventoryId String
     * @return Inventory
     */
    @Override
    public Inventory getInventoryById(String inventoryId) {
        return null;
    }

    /**
     * Updates available stock details after consumption from inventory stock and relevant log trail.
     * @param consumedInventoryStockInfo InventoryStockInfo
     * @return Inventory
     */
    @Override
    public Inventory updateConsumedInventoryStock(InventoryStockInfo consumedInventoryStockInfo) {
        return null;
    }

    /**
     * Fetches Inventory list based on given Inventory filter.
     * @param inventoryFilter
     * @return
     */
    @Override
    public List<Inventory> getInventoryListByFilter(final InventoryFilter inventoryFilter) {
        return null;
    }

    /**
     * Fetches InventoryFilter data for given Inventory filter.
     * @param inventoryFilter InventoryFilter
     * @return InventoryFilterData
     */
    @Override
    public InventoryFilterData getInventoryListFilterData(InventoryFilter inventoryFilter) {
        return null;
    }

    /**
     * Marks a inventory as deleted.
     * @param inventoryId String
     */
    @Override
    public void deleteInventory(String inventoryId) {

    }
}
