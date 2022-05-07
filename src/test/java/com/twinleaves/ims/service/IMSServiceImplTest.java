package com.twinleaves.ims.service;

import com.twinleaves.ims.dao.InventoryDAOService;
import com.twinleaves.ims.entity.InventoryEntity;
import com.twinleaves.ims.exception.ResourceNotFoundException;
import com.twinleaves.ims.mappers.InventoryMapper;
import com.twinleaves.ims.model.Inventory;
import com.twinleaves.ims.model.InventoryFilter;
import com.twinleaves.ims.model.InventoryFilterData;
import com.twinleaves.ims.repository.InventoryRepository;
import org.apache.commons.collections.CollectionUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IMSServiceImplTest {

    @MockBean
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryDAOService inventoryDAOService;

    @Autowired
    private InventoryMapper inventoryMapper;

    @MockBean
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @InjectMocks
    @Autowired
    private IMSServiceImpl imsServiceImpl;

    @BeforeAll
    private void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addInventory_with_success_scenario() {
        Inventory inventory = new Inventory();
        inventory.setProductId("123");
        InventoryEntity inventoryEntitySaved = new InventoryEntity();
        inventoryEntitySaved.setProductId("123");
        assertDoesNotThrow(() -> {
            doReturn(inventoryEntitySaved).when(inventoryRepository).save(any(InventoryEntity.class));
            Inventory inventorySaved = imsServiceImpl.addInventory(inventory);
            assertEquals(inventory.getProductId(), inventorySaved.getProductId(), "Inventory productId must be same");
        });
    }

    @Test
    void addInventory_with_failed_scenario_while_saving_to_db() {
        Inventory inventory = new Inventory();
        doThrow(new RuntimeException("Exception while saving data to DB")).when(inventoryRepository).save(any(InventoryEntity.class));
        Exception exception = assertThrows(Exception.class, () -> {
            imsServiceImpl.addInventory(inventory);
        });
        assertNotNull(exception, "Exception should be thrown by the method");
    }

    @Test
    void getInventoryById_with_success_scenario() {
        String productId = "1234";
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setProductId(productId);
        doReturn(Optional.of(inventoryEntity)).when(inventoryRepository).findById(anyString());
        assertDoesNotThrow(() -> {
            Inventory fetchedInventory = imsServiceImpl.getInventoryById(productId);
            assertEquals(productId, fetchedInventory.getProductId());
        });
    }

    @Test
    void getInventoryById_with_null_inventoryId() {
        assertDoesNotThrow(() -> {
            Inventory fetchedInventory = imsServiceImpl.getInventoryById(null);
            assertNull(fetchedInventory, "Should not return any object for null input of inventory id");
        });
    }

    @Test
    void getInventoryById_with_empty_inventoryId() {
        assertDoesNotThrow(() -> {
            Inventory fetchedInventory = imsServiceImpl.getInventoryById("");
            assertNull(fetchedInventory, "Should not return any object for empty input of inventory id");
        });
    }

    @Test
    void getInventoryById_with_non_existing_inventoryId_scenario() {
        String inventoryId = "ABCD123";
        doThrow(new NoSuchElementException()).when(inventoryRepository).findById(inventoryId);
        assertDoesNotThrow(() -> {
            Inventory fetchedInventory = imsServiceImpl.getInventoryById(inventoryId);
            assertNull(fetchedInventory, "Should not return any object for non existing input of inventory id");
        });
    }

    @Test
    void getInventoryListFilterData_with_all_filter_data_and_no_results() {
        InventoryFilter inventoryFilter = InventoryFilter.builder()
                .inventoryId(Arrays.asList("1"))
                .productId(Arrays.asList("2"))
                .category(Arrays.asList("2"))
                .locationId(Arrays.asList("2"))
                .locationType(Arrays.asList("2"))
                .sourceInventory(Arrays.asList("2"))
                .build();
        SqlRowSet sqlRowSet = mock(SqlRowSet.class);
        doReturn(false).when(sqlRowSet).next();
        doReturn(sqlRowSet).when(namedParameterJdbcTemplate).queryForRowSet(anyString(), any(MapSqlParameterSource.class));
        assertDoesNotThrow(() -> {
            InventoryFilterData inventoryFilterData = imsServiceImpl.getInventoryListFilterData(inventoryFilter);
            assertNotNull(inventoryFilterData, "Filter data cannot be null");
            assertNotNull(inventoryFilterData.getInventoryList(), "List cannot be null");
        });
    }

    @Test
    void getInventoryListFilterData_with_filter_data_results() {
        InventoryFilter inventoryFilter = InventoryFilter.builder()
                .inventoryId(Arrays.asList("1"))
                .productId(Arrays.asList("23"))
                .category(Arrays.asList("24"))
                .locationId(Arrays.asList("52"))
                .locationType(Arrays.asList("2T"))
                .sourceInventory(Arrays.asList("2"))
                .build();
        SqlRowSet sqlRowSet = mock(SqlRowSet.class);
        // Simulate with single result set and return false for next iteration
        doReturn(true, false).when(sqlRowSet).next();
        doReturn("sample").when(sqlRowSet).getString(anyString());
        doReturn(1).when(sqlRowSet).getInt(anyString());
        doReturn(1.0).when(sqlRowSet).getDouble(anyString());
        doReturn(sqlRowSet).when(namedParameterJdbcTemplate).queryForRowSet(anyString(), any(MapSqlParameterSource.class));
        assertDoesNotThrow(() -> {
            InventoryFilterData inventoryFilterData = imsServiceImpl.getInventoryListFilterData(inventoryFilter);
            assertNotNull(inventoryFilterData, "Filter data cannot be null");
            assertTrue(CollectionUtils.isNotEmpty(inventoryFilterData.getInventoryList()), "List cannot be empty");
        });
    }

    @Test
    void deleteInventory_with_success_scenario() {
        String inventoryId = "890";
        InventoryEntity inventoryEntity = new InventoryEntity();
        doReturn(Optional.of(inventoryEntity)).when(inventoryRepository).findById(anyString());
        doReturn(inventoryEntity).when(inventoryRepository).save(inventoryEntity);
        assertDoesNotThrow(() -> {
            imsServiceImpl.deleteInventory(inventoryId);
        });
    }

    @Test
    void deleteInventory_with_non_existing_inventory_id_scenario() {
        String inventoryId = "123T";
        doThrow(new NoSuchElementException()).when(inventoryRepository).findById(anyString());
        assertThrows(ResourceNotFoundException.class, () -> {
            imsServiceImpl.deleteInventory(inventoryId);
        });
    }

    @Test
    void deleteInventory_with_null_inventory_id_scenario() {
        assertThrows(ResourceNotFoundException.class, () -> {
            imsServiceImpl.deleteInventory(null);
        });
    }

    @Test
    void deleteInventory_with_empty_inventory_id_scenario() {
        assertThrows(ResourceNotFoundException.class, () -> {
            imsServiceImpl.deleteInventory(null);
        });
    }

}