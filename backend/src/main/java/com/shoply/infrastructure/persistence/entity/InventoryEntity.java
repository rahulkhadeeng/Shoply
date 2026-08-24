package com.shoply.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "inventory")
public class InventoryEntity {
  @Id @Column(name = "product_id") private UUID productId;
  @Column(nullable = false) private int quantity;
  protected InventoryEntity() {}
  public InventoryEntity(UUID productId, int quantity) { this.productId=productId; this.quantity=quantity; }
  public UUID getProductId() { return productId; }
  public int getQuantity() { return quantity; }
  public void setQuantity(int quantity) { this.quantity=quantity; }
}
