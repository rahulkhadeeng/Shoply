package com.shoply.infrastructure.persistence.repository;
import com.shoply.infrastructure.persistence.entity.UserEntity; import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface SpringDataUserRepository extends JpaRepository<UserEntity,UUID>{Optional<UserEntity> findByEmail(String email);}
