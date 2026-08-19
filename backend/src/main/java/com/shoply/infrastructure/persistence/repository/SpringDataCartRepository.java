package com.shoply.infrastructure.persistence.repository;
import com.shoply.infrastructure.persistence.entity.CartEntity; import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface SpringDataCartRepository extends JpaRepository<CartEntity,UUID>{Optional<CartEntity> findByUserId(UUID userId);}
