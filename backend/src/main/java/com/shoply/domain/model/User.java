package com.shoply.domain.model;

import java.util.UUID;

public record User(UUID id, String email, String passwordHash, String role) {
  public User { if(email == null || email.isBlank()) throw new IllegalArgumentException("Email is required"); }
}
