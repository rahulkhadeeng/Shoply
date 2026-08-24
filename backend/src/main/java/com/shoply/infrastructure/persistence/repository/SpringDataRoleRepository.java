package com.shoply.infrastructure.persistence.repository;

import com.shoply.infrastructure.persistence.entity.RoleEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataRoleRepository extends JpaRepository<RoleEntity, UUID> {
  Optional<RoleEntity> findByName(String name);
}
