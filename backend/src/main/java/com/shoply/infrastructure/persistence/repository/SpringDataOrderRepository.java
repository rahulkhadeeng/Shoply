package com.shoply.infrastructure.persistence.repository;
import com.shoply.infrastructure.persistence.entity.OrderEntity; import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface SpringDataOrderRepository extends JpaRepository<OrderEntity,UUID>{Optional<OrderEntity> findByIdAndUserId(UUID id,UUID userId);List<OrderEntity> findByUserIdOrderByCreatedAtDesc(UUID userId);}
