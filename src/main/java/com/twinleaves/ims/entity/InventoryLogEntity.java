package com.twinleaves.ims.entity;

import com.twinleaves.ims.constants.DBConstants;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="INVENTORY_LOGS", schema = DBConstants.SCHEMA)
@Data
public class InventoryLogEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "TRANSACTION_ID")
    private String transactionId;
    @Column(name = "INVENTORY_ID")
    private String inventoryId;
    @Column(name = "VERSION")
    private Integer version;
    @Column(name = "AVAILABLE_QUANTITY_CASES")
    private Double availableQuantityCases;
    @Column(name = "AVAILABLE_QUANTITY_UNITS")
    private Integer availableQuantityUnits;
    @Column(name = "AVAILABLE_WEIGHT")
    private Double availableWeight;
    @Column(name = "WEIGHT_UOM")
    private String weightUOM;
    @Column(name = "AVAILABLE_VOLUME")
    private Double availableVolume;
    @Column(name = "VOLUME_UOM")
    private String volumeUOM;
    @Column(name = "CONSUMED_QUANTITY_CASES")
    private Double consumedQuanityCases;
    @Column(name = "CONSUMED_QUANTITY_UNITS")
    private Integer consumedQuanityUnits;
    @Column(name = "CONSUMED_WEIGHT")
    private Double consumedWeight;
    @Column(name = "CONSUMED_VOLUME")
    private Double consumedVolume;
    @Column(name = "VOIDED_BY")
    private String voidedBy;
    @Column(name = "VOIDED_ON")
    private Date voidedOn;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "CREATED_ON")
    private Date createdOn;




}
