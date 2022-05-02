package com.twinleaves.ims.entity;

import com.twinleaves.ims.constants.DBConstants;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="INVENTORY", schema = DBConstants.SCHEMA)
@Data
public class InventoryEntity {

    @Id
    @Column(name = "INVENTORY_ID")
    private String inventoryId;
    @Column(name = "PRODUCT_ID")
    private String productId;
    @Column(name = "CATEGORY")
    private String category;
    @Column(name = "CATALOG_ID")
    private String catalogId;
    @Column(name = "QUANTITY_CASES")
    private Double quantityCases;
    @Column(name = "QUANTITY_UNITS")
    private Integer quantityUnits;
    @Column(name = "VOLUME")
    private Double volume;
    @Column(name = "VOLUME_UOM")
    private String volumeUOM;
    @Column(name = "WEIGHT")
    private Double weight;
    @Column(name = "WEIGHT_UOM")
    private String weightUOM;
    @Column(name = "LOCATION_ID")
    private String locationId;
    @Column(name = "LOCATION_TYPE")
    private String locationType;
    @Column(name = "AVAILABLE_CASES")
    private Double availableCases;
    @Column(name = "AVAILABLE_UNITS")
    private Integer availableUnits;
    @Column(name = "AVAILABLE_VOLUME")
    private Double availableVolume;
    @Column(name = "AVAILABLE_WEIGHT")
    private Double availableWeight;
    @Column(name = "SOURCE_INVENTORY")
    private String sourceInventory;
    @Column(name = "INWARDED_ON")
    private Date inwardedOn;
    @Column(name = "INWARDED_BY")
    private String inwardedBy;
    @Column(name = "EXPIRATION_DATE")
    private Date expirationDate;
    @Column(name = "ITEM_CODE")
    private String itemCode;
    @Column(name = "ITEM_NAME")
    private String itemName;
    @Column(name = "DEPARTMENT")
    private String department;
    @Column(name = "BRAND")
    private String brand;
    @Column(name = "SHORT_NAME")
    private String shortName;
    @Column(name = "REMARKS")
    private String remarks;
    @Column(name = "MERCHANDISE")
    private String merchandise;
    @Column(name = "ITEM_TYPE")
    private String itemType;
    @Column(name = "STOCK_TYPE")
    private String stockType;
    @Column(name = "BATCH_NO")
    private String batchNo;
    @Column(name = "STYLE")
    private String style;
    @Column(name = "IS_DELETED")
    private String isDeleted;


}
