package com.twinleaves.ims.dao;

import com.twinleaves.ims.entity.InventoryEntity;
import com.twinleaves.ims.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryDAOService {

    private InventoryRepository inventoryRepository;

    @Autowired
    public InventoryDAOService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Saves InventoryEntity to DB.
     * @param inventoryEntity InventoryEntity
     * @return InventoryEntity
     */
    public InventoryEntity saveInventory(final InventoryEntity inventoryEntity) {
        InventoryEntity entity = inventoryRepository.save(inventoryEntity);
        return entity;
    }

    /**
     * Fetches InventoryEntity with the given inventoryId.
     * @param inventoryId String
     * @return InventoryEntity
     */
    public InventoryEntity fetchInventoryEntityByID(final String inventoryId) {
        InventoryEntity inventoryEntity = inventoryRepository.findById(inventoryId).get();
        return inventoryEntity;
    }

    /**
     * Marks inventory as deleted.
     * @param inventoryId String
     */
    public void deleteInventory(final String inventoryId) {
        InventoryEntity inventoryEntity = inventoryRepository.findById(inventoryId).get();
        if (inventoryEntity != null) {
            inventoryEntity.setIsDeleted("Y");
            inventoryRepository.save(inventoryEntity);
        }
    }
}
