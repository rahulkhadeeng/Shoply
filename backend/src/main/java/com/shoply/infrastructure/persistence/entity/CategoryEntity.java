package com.shoply.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity @Table(name = "categories")
public class CategoryEntity {
  @Id private UUID id; @Column(nullable = false) private String name; @Column(nullable = false, unique = true) private String slug;
  @Column(nullable = false) private String icon; @Column(name = "item_count", nullable = false) private int itemCount;
  protected CategoryEntity() {}
  public CategoryEntity(UUID id, String name, String slug, String icon, int itemCount) { this.id=id;this.name=name;this.slug=slug;this.icon=icon;this.itemCount=itemCount; }
  public UUID getId(){return id;} public String getName(){return name;} public String getSlug(){return slug;} public String getIcon(){return icon;} public int getItemCount(){return itemCount;}
}
