package com.shoply.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.shoply.domain.model.Category;
import com.shoply.domain.model.Product;
import com.shoply.domain.repository.CategoryRepository;
import com.shoply.domain.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CatalogQueryServiceTest {
  @Test
  void returnsFeaturedProductsAsApplicationDtos() {
    Category category = new Category(UUID.randomUUID(), "Electronics", "electronics", "Laptop", 1);
    Product product = new Product(UUID.randomUUID(), "Desk Lamp", "desk-lamp", "Warm light", new BigDecimal("49.00"), null, new BigDecimal("4.8"), "lamp.jpg", List.of("lamp.jpg"), true, category);
    ProductRepository products = new ProductRepository() {
      public List<Product> findAll() { return List.of(product); }
      public List<Product> findFeatured() { return List.of(product); }
      public List<Product> searchByName(String q) { return List.of(product); }
      public Optional<Product> findById(UUID id) { return Optional.of(product); }
    };
    CategoryRepository categories = new CategoryRepository() {
      public List<Category> findAll() { return List.of(category); }
      public Optional<Category> findById(UUID id) { return Optional.of(category); }
    };
    var result = new CatalogQueryService(products, categories).featured();
    assertEquals("Desk Lamp", result.getFirst().name());
    assertEquals("Electronics", result.getFirst().category().name());
  }
}
