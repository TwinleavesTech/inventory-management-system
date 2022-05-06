package com.twinleaves.ims.service;

import com.twinleaves.ims.annotation.LogExecutionTime;
import com.twinleaves.ims.controller.IMSController;
import com.twinleaves.ims.dao.InventoryDAOService;
import com.twinleaves.ims.entity.InventoryEntity;
import com.twinleaves.ims.mappers.InventoryMapper;
import com.twinleaves.ims.model.Inventory;
import com.twinleaves.ims.model.InventoryFilter;
import com.twinleaves.ims.model.InventoryFilterData;
import com.twinleaves.ims.model.InventoryStockInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
    @LogExecutionTime("Adding inventory to DB")
    @Override
    public Inventory addInventory(final Inventory inventory) {
        InventoryEntity inventoryEntity;
        Inventory detail = null;
        try {
            inventoryEntity = inventoryDAOService.saveInventory(inventoryMapper.inventoryToInventoryEntity(inventory));
            log.info("Saved inventory data ::: " + inventoryEntity);
            detail = inventoryMapper.inventoryEntityToInventory(inventoryEntity);
        } catch (Exception e) {
            log.error("Exception occurred while saving inventory entity to DB", e);
            throw e;
        }
        return detail;
    }

    /**
     * Fetches Inventory details of given Inventory.
     * @param inventoryId String
     * @return Inventory
     */
    @LogExecutionTime("Fetching Inventory from DB")
    @Override
    public Inventory getInventoryById(final String inventoryId) {
        Inventory inventory = null;
        if (StringUtils.isNoneEmpty(inventoryId)) {
            try {
                InventoryEntity inventoryEntity = inventoryDAOService.fetchInventoryEntityByID(inventoryId);
                if (inventoryEntity != null) {
                    log.info("Fetched inventory for inventoryId {} ", inventoryId);
                    inventory = inventoryMapper.inventoryEntityToInventory(inventoryEntity);
                }
            } catch (NoSuchElementException e) {
                log.info("No such InventoryEntity for inventoryID - {}", inventoryId);
            }
        }
        return inventory;
    }

    /**
     * Updates available stock details after consumption from inventory stock and relevant log trail.
     * @param consumedInventoryStockInfo InventoryStockInfo
     * @return Inventory
     */
    @Override
    public Inventory updateConsumedInventoryStock(final InventoryStockInfo consumedInventoryStockInfo) {
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
