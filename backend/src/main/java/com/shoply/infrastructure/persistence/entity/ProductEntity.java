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
  @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "category_id", nullable = false) private CategoryEntity category;
  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @OrderBy("position ASC")
  private java.util.List<ProductImageEntity> images = new java.util.ArrayList<>();
  protected ProductEntity() {}
  public ProductEntity(UUID id, String name, String slug, String description, BigDecimal price, BigDecimal previousPrice, BigDecimal rating, String imageUrl, boolean featured, CategoryEntity category) { this.id=id;this.name=name;this.slug=slug;this.description=description;this.price=price;this.previousPrice=previousPrice;this.rating=rating;this.imageUrl=imageUrl;this.featured=featured;this.category=category; }
  public UUID getId(){return id;} public String getName(){return name;} public String getSlug(){return slug;} public String getDescription(){return description;} public BigDecimal getPrice(){return price;} public BigDecimal getPreviousPrice(){return previousPrice;} public BigDecimal getRating(){return rating;} public String getImageUrl(){return imageUrl;} public boolean isFeatured(){return featured;} public CategoryEntity getCategory(){return category;}
  public java.util.List<ProductImageEntity> getImages() { return images; }
}
