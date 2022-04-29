package com.twinleaves.ims.dao;

import com.twinleaves.ims.entity.InventoryEntity;
import com.twinleaves.ims.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryDAOService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public InventoryEntity saveInventory(final InventoryEntity inventoryEntity) {
        InventoryEntity entity = inventoryRepository.save(inventoryEntity);
        return entity;
    }
}
