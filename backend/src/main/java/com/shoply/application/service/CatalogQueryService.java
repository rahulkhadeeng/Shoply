package com.shoply.application.service;

import com.shoply.application.dto.*;
import com.shoply.domain.model.*;
import com.shoply.domain.repository.*;
import java.util.*;
public class CatalogQueryService {
  private final ProductRepository products; private final CategoryRepository categories;
  public CatalogQueryService(ProductRepository products, CategoryRepository categories) { this.products = products; this.categories = categories; }
  public List<ProductDto> products() { return products.findAll().stream().map(this::product).toList(); }
  public List<ProductDto> featured() { return products.findFeatured().stream().map(this::product).toList(); }
  public List<ProductDto> search(String query) { return products.searchByName(query).stream().map(this::product).toList(); }
  public ProductDto productById(UUID id) { return product(products.findById(id).orElseThrow(() -> new NoSuchElementException("Product not found"))); }
  public List<CategoryDto> categories() { return categories.findAll().stream().map(this::category).toList(); }
  public CategoryDto categoryById(UUID id) { return category(categories.findById(id).orElseThrow(() -> new NoSuchElementException("Category not found"))); }
  private ProductDto product(Product p) { return new ProductDto(p.id(),p.name(),p.slug(),p.description(),p.price(),p.previousPrice(),p.rating(),p.imageUrl(),p.featured(),category(p.category())); }
  private CategoryDto category(Category c) { return new CategoryDto(c.id(),c.name(),c.slug(),c.icon(),c.itemCount()); }
}
