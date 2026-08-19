package com.shoply.infrastructure.persistence.repository;
import com.shoply.infrastructure.persistence.entity.CategoryEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SpringDataCategoryRepository extends JpaRepository<CategoryEntity, UUID> {}
