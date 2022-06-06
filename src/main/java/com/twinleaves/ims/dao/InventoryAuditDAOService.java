package com.twinleaves.ims.dao;

import com.twinleaves.ims.entity.InventoryEntity;
import com.twinleaves.ims.entity.InventoryLogEntity;
import com.twinleaves.ims.model.Inventory;
import com.twinleaves.ims.model.InventoryStockInfo;
import com.twinleaves.ims.repository.InventoryLogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class InventoryAuditDAOService {

    private InventoryLogsRepository inventoryLogsRepository;

    @Autowired
    public InventoryAuditDAOService(final InventoryLogsRepository inventoryLogsRepository) {
        this.inventoryLogsRepository = inventoryLogsRepository;
    }

    /**
     * Saves current transactional log to DB.
     * Requires an existing transaction before adding data to inventory.
     * @param inventory InventoryEntity
     * @param consumedStockInfo InventoryStockInfo
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void saveCurrentAuditRecord(final Inventory inventory, final InventoryStockInfo consumedStockInfo) {
        InventoryLogEntity inventoryLogEntity = new InventoryLogEntity();
        inventoryLogEntity.setVersion(inventory.getVersion());
        inventoryLogEntity.setInventoryId(inventory.getInventoryId());
        inventoryLogEntity.setAvailableQuantityCases(inventory.getAvailableCases());
        inventoryLogEntity.setAvailableQuantityUnits(inventory.getAvailableUnits());
        inventoryLogEntity.setAvailableWeight(inventory.getAvailableWeight());
        inventoryLogEntity.setAvailableVolume(inventory.getAvailableVolume());
        inventoryLogEntity.setWeightUOM(inventory.getWeightUOM());
        inventoryLogEntity.setVolumeUOM(inventory.getVolumeUOM());
        inventoryLogEntity.setConsumedQuanityCases(consumedStockInfo.getQuantityCases());
        inventoryLogEntity.setConsumedQuanityUnits(consumedStockInfo.getQuantityUnits());
        inventoryLogEntity.setConsumedWeight(consumedStockInfo.getWeight());
        inventoryLogEntity.setConsumedVolume(consumedStockInfo.getVolume());
        inventoryLogEntity.setCreatedBy(consumedStockInfo.getCreatedBy());
        inventoryLogEntity.setCreatedOn(consumedStockInfo.getCreatedOn());
        inventoryLogsRepository.save(inventoryLogEntity);
    }
}
