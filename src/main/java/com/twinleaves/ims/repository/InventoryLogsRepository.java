package com.twinleaves.ims.repository;

import com.twinleaves.ims.entity.InventoryLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryLogsRepository extends JpaRepository<InventoryLogEntity, String> {
}
