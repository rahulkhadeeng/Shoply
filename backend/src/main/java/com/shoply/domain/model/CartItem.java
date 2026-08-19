package com.shoply.domain.model;

import java.util.UUID;

public record CartItem(UUID productId, int quantity) {
  public CartItem { if (productId == null) throw new IllegalArgumentException("Product is required"); if (quantity < 1) throw new IllegalArgumentException("Quantity must be at least one"); }
  public CartItem changeQuantity(int newQuantity) { return new CartItem(productId, newQuantity); }
}
