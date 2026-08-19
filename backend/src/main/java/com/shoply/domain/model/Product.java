package com.shoply.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record Product(UUID id, String name, String slug, String description, BigDecimal price, BigDecimal previousPrice,
                      BigDecimal rating, String imageUrl, boolean featured, Category category) {
  public Product { if (price == null || price.signum() < 0) throw new IllegalArgumentException("Price must be non-negative"); }
}
