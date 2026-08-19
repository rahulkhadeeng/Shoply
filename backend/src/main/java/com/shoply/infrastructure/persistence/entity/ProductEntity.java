package com.shoply.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity @Table(name = "products")
public class ProductEntity {
  @Id private UUID id; @Column(nullable = false) private String name; @Column(nullable = false, unique = true) private String slug;
  @Column(nullable = false, length = 1000) private String description; @Column(nullable = false, precision = 12, scale = 2) private BigDecimal price;
  @Column(name = "previous_price", precision = 12, scale = 2) private BigDecimal previousPrice; @Column(nullable = false, precision = 2, scale = 1) private BigDecimal rating;
  @Column(name = "image_url", nullable = false) private String imageUrl; @Column(nullable = false) private boolean featured;
  @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "category_id", nullable = false) private CategoryEntity category;
  protected ProductEntity() {}
  public UUID getId(){return id;} public String getName(){return name;} public String getSlug(){return slug;} public String getDescription(){return description;} public BigDecimal getPrice(){return price;} public BigDecimal getPreviousPrice(){return previousPrice;} public BigDecimal getRating(){return rating;} public String getImageUrl(){return imageUrl;} public boolean isFeatured(){return featured;} public CategoryEntity getCategory(){return category;}
}
