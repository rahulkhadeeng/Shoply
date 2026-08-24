package com.shoply.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class UserEntity {
  @Id private UUID id;
  @Column(nullable = false, unique = true) private String email;
  @Column(name = "password_hash", nullable = false) private String passwordHash;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<RoleEntity> roles = new HashSet<>();
  protected UserEntity() {}
  public UserEntity(UUID id, String email, String passwordHash, RoleEntity role) { this.id=id; this.email=email; this.passwordHash=passwordHash; roles.add(role); }
  public UUID getId() { return id; }
  public String getEmail() { return email; }
  public String getPasswordHash() { return passwordHash; }
  public String getRole() { return roles.stream().findFirst().map(RoleEntity::getName).orElse("CUSTOMER"); }
}
