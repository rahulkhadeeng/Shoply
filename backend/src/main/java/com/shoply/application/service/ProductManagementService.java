package com.shoply.application.service;

import com.shoply.application.dto.CreateProductCommand;
import com.shoply.application.dto.ProductDto;
import com.shoply.domain.model.Product;
import com.shoply.domain.repository.CategoryRepository;
import com.shoply.domain.repository.ProductManagementRepository;
import java.util.NoSuchElementException;
import java.util.UUID;

public class ProductManagementService {
  private final ProductManagementRepository products;
  private final CategoryRepository categories;
  public ProductManagementService(ProductManagementRepository products, CategoryRepository categories) { this.products=products; this.categories=categories; }
  public ProductDto create(CreateProductCommand command) {
    if (command.inventoryQuantity() < 0) throw new IllegalArgumentException("Inventory cannot be negative");
    var category = categories.findById(command.categoryId()).orElseThrow(() -> new NoSuchElementException("Category not found"));
    Product product = products.save(new Product(UUID.randomUUID(), command.name(), command.slug(), command.description(), command.price(), command.previousPrice(), command.rating(), command.imageUrl(), java.util.List.of(command.imageUrl()), command.featured(), category), command.inventoryQuantity());
    return new ProductDto(product.id(),product.name(),product.slug(),product.description(),product.price(),product.previousPrice(),product.rating(),product.imageUrl(),product.imageUrls(),product.featured(),new com.shoply.application.dto.CategoryDto(category.id(),category.name(),category.slug(),category.icon(),category.itemCount()));
  }
  public ProductDto update(UUID productId, CreateProductCommand command) {
    if (command.inventoryQuantity() < 0) throw new IllegalArgumentException("Inventory cannot be negative");
    var category = categories.findById(command.categoryId()).orElseThrow(() -> new NoSuchElementException("Category not found"));
    Product product = products.save(new Product(productId, command.name(), command.slug(), command.description(), command.price(), command.previousPrice(), command.rating(), command.imageUrl(), java.util.List.of(command.imageUrl()), command.featured(), category), command.inventoryQuantity());
    return new ProductDto(product.id(),product.name(),product.slug(),product.description(),product.price(),product.previousPrice(),product.rating(),product.imageUrl(),product.imageUrls(),product.featured(),new com.shoply.application.dto.CategoryDto(category.id(),category.name(),category.slug(),category.icon(),category.itemCount()));
  }
  public void delete(UUID productId) { products.deleteById(productId); }
}
