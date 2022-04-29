package com.twinleaves.ims.mappers;

import com.twinleaves.ims.entity.InventoryEntity;
import com.twinleaves.ims.model.Inventory;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface InventoryMapper {

    InventoryEntity inventoryToInventoryEntity(Inventory inventory);

    Inventory inventoryEntityToInventory(InventoryEntity inventoryEntity);
}
