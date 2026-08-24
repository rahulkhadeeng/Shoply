package com.shoply.infrastructure.web.controller;

import com.shoply.infrastructure.persistence.entity.ProductEntity;
import com.shoply.infrastructure.persistence.entity.ProductImageEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductImageController {
    private final EntityManager entityManager;
    public AdminProductImageController(EntityManager entityManager) { this.entityManager = entityManager; }

    @PostMapping("/{productId}/images")
    @Transactional
    public ResponseEntity<Void> saveProductImages(@PathVariable UUID productId, @Valid @RequestBody ProductImagesRequest request) {
        if (request.imageUrls().size() > 3) return ResponseEntity.badRequest().build();
        ProductEntity product = entityManager.find(ProductEntity.class, productId);
        if (product == null) return ResponseEntity.notFound().build();
        entityManager.createQuery("delete from ProductImageEntity image where image.product = :product")
                .setParameter("product", product).executeUpdate();
        for (int index = 0; index < request.imageUrls().size(); index++) entityManager.persist(new ProductImageEntity(product, request.imageUrls().get(index), index));
        return ResponseEntity.noContent().build();
    }

    public record ProductImagesRequest(@NotEmpty @Size(max = 3) List<@Size(max = 1000) String> imageUrls) { }
}
