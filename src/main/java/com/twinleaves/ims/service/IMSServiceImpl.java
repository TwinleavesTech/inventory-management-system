package com.twinleaves.ims.service;

import com.twinleaves.ims.annotation.LogExecutionTime;
import com.twinleaves.ims.controller.IMSController;
import com.twinleaves.ims.dao.InventoryDAOService;
import com.twinleaves.ims.entity.InventoryEntity;
import com.twinleaves.ims.exception.ResourceNotFoundException;
import com.twinleaves.ims.mappers.InventoryMapper;
import com.twinleaves.ims.model.Inventory;
import com.twinleaves.ims.model.InventoryFilter;
import com.twinleaves.ims.model.InventoryFilterData;
import com.twinleaves.ims.model.InventoryStockInfo;
import com.twinleaves.ims.query.IMSQueryCatalog;
import com.twinleaves.ims.query.QueryBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class IMSServiceImpl implements IMSService {

    private static final Logger log = LoggerFactory.getLogger(IMSController.class);

    private InventoryDAOService inventoryDAOService;

    private InventoryMapper inventoryMapper;

    private IMSQueryCatalog imsQueryCatalog;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public IMSServiceImpl(InventoryDAOService inventoryDAOService, InventoryMapper inventoryMapper
            , IMSQueryCatalog imsQueryCatalog, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.inventoryDAOService = inventoryDAOService;
        this.inventoryMapper = inventoryMapper;
        this.imsQueryCatalog = imsQueryCatalog;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
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
    public void updateConsumedInventoryStock(final InventoryStockInfo consumedInventoryStockInfo) {
        if (consumedInventoryStockInfo != null && StringUtils.isNotEmpty(consumedInventoryStockInfo.getInventoryId())) {
            try {
                inventoryDAOService.updateConsumedStockInfo(consumedInventoryStockInfo);
            } catch (NoSuchElementException e) {
                log.info("No such InventoryEntity for inventoryID - {}", consumedInventoryStockInfo.getInventoryId());
                throw new ResourceNotFoundException("No such inventory");
            } catch (Exception e) {
                log.error("Exception occurred while updating inventory stock info", e);
            }
        } else {
            throw new ResourceNotFoundException("No such inventory");
        }
    }

    /**
     * Fetches InventoryFilter data for given Inventory filter.
     * @param inventoryFilter InventoryFilter
     * @return InventoryFilterData
     */
    @Override
    public InventoryFilterData getInventoryListFilterData(final InventoryFilter inventoryFilter) {
        InventoryFilterData inventoryFilterData = new InventoryFilterData();
        String query = imsQueryCatalog.getImsFilterQuery();
        QueryBuilder queryBuilder = new QueryBuilder(query, namedParameterJdbcTemplate);
        log.debug("Building Inventory filter query based on filter criteria");
        // InventoryId filter criteria
        if (CollectionUtils.isNotEmpty(inventoryFilter.getInventoryId())) {
            queryBuilder.addParameterWithEnabledCriteria("INVENTORY_ID", "INVENTORY_ID", inventoryFilter.getInventoryId());
        }
        // PRODUCT_ID filter criteria
        if (CollectionUtils.isNotEmpty(inventoryFilter.getProductId())) {
            queryBuilder.addParameterWithEnabledCriteria("PRODUCT_ID", "PRODUCT_ID", inventoryFilter.getProductId());
        }
        // CATEGORY filter criteria
        if (CollectionUtils.isNotEmpty(inventoryFilter.getCategory())) {
            queryBuilder.addParameterWithEnabledCriteria("CATEGORY", "CATEGORY", inventoryFilter.getCategory());
        }
        // LOCATION_ID filter criteria
        if (CollectionUtils.isNotEmpty(inventoryFilter.getLocationId())) {
            queryBuilder.addParameterWithEnabledCriteria("LOCATION_ID", "LOCATION_ID", inventoryFilter.getLocationId());
        }
        // LOCATION_TYPE filter criteria
        if (CollectionUtils.isNotEmpty(inventoryFilter.getLocationType())) {
            queryBuilder.addParameterWithEnabledCriteria("LOCATION_TYPE", "LOCATION_TYPE", inventoryFilter.getLocationType());
        }
        // SOURCE_INVENTORY filter criteria
        if (CollectionUtils.isNotEmpty(inventoryFilter.getSourceInventory())) {
            queryBuilder.addParameterWithEnabledCriteria("SOURCE_INVENTORY", "SOURCE_INVENTORY", inventoryFilter.getSourceInventory());
        }
        log.debug("Query built with filter criteria {}", queryBuilder.getQuery());
        List<Inventory> inventoryList = new ArrayList<>();
        log.debug("Querying for inventory data with filter criteria");
        SqlRowSet sqlRowSet = namedParameterJdbcTemplate.queryForRowSet(queryBuilder.getQuery(), queryBuilder.getSqlParams());
        while (sqlRowSet.next()) {
            inventoryList.add(getInventoryFromRowSet(sqlRowSet));
        }
        inventoryFilterData.setInventoryList(inventoryList);
        return inventoryFilterData;
    }

    /**
     * Marks inventory as deleted.
     * @param inventoryId String
     */
    @Override
    public void deleteInventory(final String inventoryId) {
        if (StringUtils.isNoneEmpty(inventoryId)) {
            try {
                inventoryDAOService.deleteInventory(inventoryId);
            } catch (NoSuchElementException e) {
                log.info("No such InventoryEntity for inventoryID - {}", inventoryId);
                throw new ResourceNotFoundException("No such inventory");
            } catch (Exception e) {
                log.error("Exception occurred while deleting inventory - {}", inventoryId, e);
                throw e;
            }
        } else {
            throw new ResourceNotFoundException("No such inventory");
        }
    }

    /**
     * Returns Inventory from given sqlRowSet.
     * @param sqlRowSet SqlRowSet
     * @return Inventory
     */
    private Inventory getInventoryFromRowSet(final SqlRowSet sqlRowSet) {
        Inventory inventory = new Inventory();
        inventory.setInventoryId(sqlRowSet.getString("INVENTORY_ID"));
        inventory.setProductId(sqlRowSet.getString("PRODUCT_ID"));
        inventory.setCategory(sqlRowSet.getString("CATEGORY"));
        inventory.setQuantityCases(sqlRowSet.getDouble("QUANTITY_CASES"));
        inventory.setQuantityUnits(sqlRowSet.getInt("QUANTITY_UNITS"));
        inventory.setVolume(sqlRowSet.getDouble("VOLUME"));
        inventory.setVolumeUOM(sqlRowSet.getString("VOLUME_UOM"));
        inventory.setWeight(sqlRowSet.getDouble("WEIGHT"));
        inventory.setWeightUOM(sqlRowSet.getString("WEIGHT_UOM"));
        inventory.setLocationId(sqlRowSet.getString("LOCATION_ID"));
        inventory.setLocationType(sqlRowSet.getString("LOCATION_TYPE"));
        inventory.setAvailableCases(sqlRowSet.getDouble("AVAILABLE_CASES"));
        inventory.setAvailableUnits(sqlRowSet.getInt("AVAILABLE_UNITS"));
        inventory.setAvailableVolume(sqlRowSet.getDouble("AVAILABLE_VOLUME"));
        inventory.setAvailableWeight(sqlRowSet.getDouble("AVAILABLE_WEIGHT"));
        inventory.setSourceInventory(sqlRowSet.getString("SOURCE_INVENTORY"));
        inventory.setInwardedOn(sqlRowSet.getString("INWARDED_ON"));
        inventory.setInwardedBy(sqlRowSet.getString("INWARDED_BY"));
        inventory.setExpirationDate(sqlRowSet.getString("EXPIRATION_DATE"));
        inventory.setItemCode(sqlRowSet.getString("ITEM_CODE"));
        inventory.setItemName(sqlRowSet.getString("ITEM_NAME"));
        inventory.setDepartment(sqlRowSet.getString("DEPARTMENT"));
        inventory.setBrand(sqlRowSet.getString("BRAND"));
        inventory.setShortName(sqlRowSet.getString("SHORT_NAME"));
        inventory.setRemarks(sqlRowSet.getString("REMARKS"));
        inventory.setMerchandise(sqlRowSet.getString("MERCHANDISE"));
        inventory.setItemType(sqlRowSet.getString("ITEM_TYPE"));
        inventory.setStockType(sqlRowSet.getString("STOCK_TYPE"));
        inventory.setBatchNo(sqlRowSet.getString("BATCH_NO"));
        inventory.setStyle(sqlRowSet.getString("STYLE"));
        inventory.setIsDeleted(sqlRowSet.getString("IS_DELETED"));
        inventory.setCatalogId(sqlRowSet.getString("CATALOG_ID"));
        inventory.setTenantId(sqlRowSet.getString("TENANT_ID"));
        return inventory;
    }
}
