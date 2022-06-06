package com.twinleaves.ims.dao;

import com.twinleaves.ims.entity.InventoryEntity;
import com.twinleaves.ims.exception.IMSInvalidDataException;
import com.twinleaves.ims.mappers.InventoryMapper;
import com.twinleaves.ims.model.Inventory;
import com.twinleaves.ims.model.InventoryStockInfo;
import com.twinleaves.ims.repository.InventoryRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;

@Service
public class InventoryDAOService {

    private static final Logger log = LoggerFactory.getLogger(InventoryDAOService.class);

    private InventoryRepository inventoryRepository;

    private InventoryAuditDAOService inventoryAuditDAOService;

    private InventoryMapper inventoryMapper;

    @Autowired
    public InventoryDAOService(InventoryRepository inventoryRepository, InventoryAuditDAOService inventoryAuditDAOService, InventoryMapper inventoryMapper) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryAuditDAOService = inventoryAuditDAOService;
        this.inventoryMapper = inventoryMapper;
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

    /**
     * Method updates consumed stock info to the inventory.
     * @param inventoryStockInfo InventoryStockInfo
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void updateConsumedStockInfo(final InventoryStockInfo inventoryStockInfo) {
        if (inventoryStockInfo != null && StringUtils.isNotEmpty(inventoryStockInfo.getInventoryId())) {
            InventoryEntity inventoryEntity = inventoryRepository.findById(inventoryStockInfo.getInventoryId()).get();
            log.debug("Fetched inventory entity: {}", inventoryEntity);
            inventoryEntity.setVersion(inventoryEntity.getVersion() + 1);
            Inventory inventory = inventoryMapper.inventoryEntityToInventory(inventoryEntity);
            if (inventoryStockInfo.getQuantityCases() != null && inventoryStockInfo.getQuantityUnits() != null) {
                if (inventoryEntity.getAvailableCases() - inventoryStockInfo.getQuantityCases() < 0) {
                    log.error("Consuming cases - {} exceeds the existing available stock - {}", inventoryStockInfo.getQuantityCases(), inventoryEntity.getAvailableCases());
                    throw new IMSInvalidDataException("Consuming cases exceeds the existing available stock cases");
                }
                if (inventoryEntity.getAvailableUnits() - inventoryStockInfo.getQuantityUnits() < 0) {
                    log.error("Consuming units - {} exceeds the existing available stock - {}", inventoryStockInfo.getQuantityUnits(), inventoryEntity.getAvailableUnits());
                    throw new IMSInvalidDataException("Consuming units exceeds the existing available stock units");
                }
                inventoryEntity.setAvailableCases(inventoryEntity.getAvailableCases() - inventoryStockInfo.getQuantityCases());
                inventoryEntity.setAvailableUnits(inventoryEntity.getQuantityUnits() - inventoryStockInfo.getQuantityUnits());
            }
            if (inventoryStockInfo.getVolume() != null) {
                if (inventoryEntity.getAvailableVolume() - inventoryStockInfo.getVolume() < 0) {
                    log.error("Consuming volume - {} exceeds the existing available stock volume - {}", inventoryStockInfo.getVolume(), inventoryEntity.getAvailableVolume());
                    throw new IMSInvalidDataException("Consuming volume exceeds the existing available stock volume");
                }
                inventoryEntity.setAvailableVolume(inventoryEntity.getAvailableVolume() - inventoryStockInfo.getVolume());
            }
            if (inventoryStockInfo.getWeight() != null) {
                if (inventoryEntity.getAvailableWeight() - inventoryStockInfo.getWeight() < 0) {
                    log.error("Consuming weight - {} exceeds the existing available stock weight - {}", inventoryStockInfo.getWeight(), inventoryEntity.getAvailableWeight());
                    throw new IMSInvalidDataException("Consuming volume exceeds the existing available stock weight");
                }
                inventoryEntity.setWeight(inventoryEntity.getAvailableWeight() - inventoryStockInfo.getWeight());
            }
            log.debug("Updated values of inventory entity : {}", inventoryEntity);
            inventoryAuditDAOService.saveCurrentAuditRecord(inventory, inventoryStockInfo);
            inventoryRepository.save(inventoryEntity);
        }
    }
}
