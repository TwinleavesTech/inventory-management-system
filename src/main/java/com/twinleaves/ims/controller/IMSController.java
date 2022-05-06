package com.twinleaves.ims.controller;

import com.twinleaves.ims.exception.ErrorFieldsMessage;
import com.twinleaves.ims.exception.ErrorMessage;
import com.twinleaves.ims.exception.ResourceNotFoundException;
import com.twinleaves.ims.model.Inventory;
import com.twinleaves.ims.model.InventoryFilter;
import com.twinleaves.ims.model.InventoryFilterData;
import com.twinleaves.ims.service.IMSService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class IMSController {

    private static final Logger log = LoggerFactory.getLogger(IMSController.class);

    @Value("${version}")
    private String version;

    @Autowired
    private IMSService imsService;

    @Operation(summary = "Fetch current application version")
    @ApiResponse(responseCode =  "200", description = "Displays current application version", content = @Content(schema = @Schema(implementation = String.class)))
    @GetMapping(value = "/app-version")
    public String getVersion() {
        return version;
    }

    @Operation(summary = "Add inventory")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "201", description = "Success", content = @Content(schema = @Schema(implementation = Inventory.class)))
            , @ApiResponse(responseCode = "400", description = "Bad data", content = @Content(schema = @Schema(implementation = ErrorFieldsMessage.class)))
            , @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
        }
    )
    @PutMapping(value = "/inventory", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addInventory(@Valid @RequestBody final Inventory inventory) {
        Inventory inventoryResponse = imsService.addInventory(inventory);
        return new ResponseEntity(inventoryResponse, HttpStatus.CREATED);
    }


    @Operation(summary = "Fetch inventory")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "Success", content = @Content(schema = @Schema(implementation = Inventory.class)))
            , @ApiResponse(responseCode = "204", description = "Not Found")
            , @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
        }
    )
    @GetMapping(value = "/inventory/{inventory-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getInventory(@Valid @PathVariable("inventory-id") final String inventoryId) {
        Inventory inventory = imsService.getInventoryById(inventoryId);
        if (inventory == null) {
            throw new ResourceNotFoundException("No such inventory");
        }
        return new ResponseEntity(inventory, HttpStatus.OK);
    }

    @Operation(summary = "Fetch inventory list based on filter criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "Success", content = @Content(schema = @Schema(implementation = InventoryFilterData.class)))
            , @ApiResponse(responseCode = "400", description = "Bad data", content = @Content(schema = @Schema(implementation = ErrorFieldsMessage.class)))
            , @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
        }
    )
    @PutMapping(value = "/inventory-list", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getInventoryBasedOnFilter(@Valid @RequestBody final InventoryFilter inventoryFilter) {
        InventoryFilterData inventoryFilterData = imsService.getInventoryListFilterData(inventoryFilter);
        return new ResponseEntity(inventoryFilterData, HttpStatus.OK);
    }

    @Operation(summary = "Delete inventory")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "Success")
            , @ApiResponse(responseCode = "204", description = "Not Found")
            , @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
        }
    )
    @DeleteMapping(value = "/inventory/{inventory-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteInventory(@Valid @PathVariable("inventory-id") final String inventoryId) {
        imsService.deleteInventory(inventoryId);
        return ResponseEntity.ok().build();
    }
}
