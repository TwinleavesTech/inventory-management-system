package com.twinleaves.ims.model;

import com.twinleaves.ims.annotation.ValidCustomDateString;
import com.twinleaves.ims.constants.IMSConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    private String inventoryId;
    @NotEmpty(message = "ProductId is mandatory")
    private String productId;
    @NotEmpty(message = "Category is mandatory")
    private String category;
    private String catalogId;
    private Double quantityCases;
    private Integer quantityUnits;
    private Double volume;
    private String volumeUOM;
    private Double weight;
    private String weightUOM;
    @NotEmpty(message = "LocationId is mandatory")
    private String locationId;
    @NotEmpty(message = "LocationType is mandatory")
    private String locationType;
    private Double availableCases;
    private Integer availableUnits;
    private Double availableVolume;
    private Double availableWeight;
    private String sourceInventory;
    private String inwardedOn;
    private String inwardedBy;
    @ValidCustomDateString(isMandatory = false, dateFormat = IMSConstants.IMS_STD_DATE_TIME_FMT, message = "Invalid expirationDate date value !! Required format : " + IMSConstants.IMS_STD_DATE_TIME_FMT)
    private String expirationDate;
    private String itemCode;
    private String itemName;
    private String department;
    private String brand;
    private String shortName;
    private String remarks;
    private String merchandise;
    private String itemType;
    private String stockType;
    private String batchNo;
    private String tenantId;
    private String style;
    private String isDeleted;
}
