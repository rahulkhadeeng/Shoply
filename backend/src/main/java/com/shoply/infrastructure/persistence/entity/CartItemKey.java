package com.shoply.infrastructure.persistence.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class CartItemKey implements Serializable {
  private UUID cart;
  private UUID productId;
  public CartItemKey() {}
  @Override public boolean equals(Object other) {
    if (this == other) return true;
    if (!(other instanceof CartItemKey that)) return false;
    return Objects.equals(cart, that.cart) && Objects.equals(productId, that.productId);
  }
  @Override public int hashCode() { return Objects.hash(cart, productId); }
}
