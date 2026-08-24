package com.shoply.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "product_images")
public class ProductImageEntity {
    @Id
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(name = "image_url", nullable = false, length = 1000)
    private String imageUrl;

    @Column(nullable = false)
    private int position;

    protected ProductImageEntity() { }

    public ProductImageEntity(ProductEntity product, String imageUrl, int position) {
        this.id = UUID.randomUUID(); this.product = product; this.imageUrl = imageUrl; this.position = position;
    }

    public UUID getId() { return id; }
    public ProductEntity getProduct() { return product; }
    public String getImageUrl() { return imageUrl; }
    public int getPosition() { return position; }
}
