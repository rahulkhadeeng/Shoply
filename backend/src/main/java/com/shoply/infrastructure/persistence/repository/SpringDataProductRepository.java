package com.shoply.infrastructure.persistence.repository;
import com.shoply.infrastructure.persistence.entity.ProductEntity;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SpringDataProductRepository extends JpaRepository<ProductEntity, UUID> {
  List<ProductEntity> findByFeaturedTrue(); List<ProductEntity> findByNameContainingIgnoreCase(String query);
}
