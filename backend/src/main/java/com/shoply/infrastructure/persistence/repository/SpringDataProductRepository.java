package com.shoply.infrastructure.persistence.repository;
import com.shoply.infrastructure.persistence.entity.ProductEntity;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SpringDataProductRepository extends JpaRepository<ProductEntity, UUID> {
  List<ProductEntity> findByFeaturedTrue();
  long countByCategoryId(UUID categoryId);
  List<ProductEntity> findByNameContainingIgnoreCase(String query);
}
