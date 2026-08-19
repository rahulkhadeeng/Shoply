package com.shoply.infrastructure.persistence.entity;
import jakarta.persistence.*;
import java.util.UUID;
@Entity @Table(name="users") public class UserEntity { @Id private UUID id; @Column(nullable=false,unique=true) private String email; @Column(name="password_hash",nullable=false) private String passwordHash; protected UserEntity(){} public UserEntity(UUID id,String email,String passwordHash){this.id=id;this.email=email;this.passwordHash=passwordHash;} public UUID getId(){return id;} public String getEmail(){return email;} public String getPasswordHash(){return passwordHash;} }
