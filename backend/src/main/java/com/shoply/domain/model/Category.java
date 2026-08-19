package com.shoply.domain.model;

import java.util.UUID;

public record Category(UUID id, String name, String slug, String icon, int itemCount) {
  public Category { if (name == null || name.isBlank()) throw new IllegalArgumentException("Category name is required"); }
}
