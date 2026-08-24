package com.shoply.infrastructure.persistence.repository;

import com.shoply.infrastructure.persistence.entity.InventoryEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataInventoryRepository extends JpaRepository<InventoryEntity, UUID> { long countByQuantityLessThanEqual(int quantity); }
