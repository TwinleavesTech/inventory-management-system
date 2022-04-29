package com.twinleaves.ims.repository;

import com.twinleaves.ims.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<InventoryEntity, String> {

}