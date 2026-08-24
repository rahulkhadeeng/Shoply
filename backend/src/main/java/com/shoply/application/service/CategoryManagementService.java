package com.shoply.application.service;

import com.shoply.application.dto.*;
import com.shoply.domain.model.Category;
import com.shoply.domain.repository.CategoryManagementRepository;
import java.util.UUID;

public class CategoryManagementService {
  private final CategoryManagementRepository categories;
  public CategoryManagementService(CategoryManagementRepository categories) { this.categories=categories; }
  public CategoryDto create(CategoryCommand command) { return dto(categories.save(new Category(UUID.randomUUID(),command.name(),command.slug(),command.icon(),0))); }
  public CategoryDto update(UUID id, CategoryCommand command) { return dto(categories.save(new Category(id,command.name(),command.slug(),command.icon(),0))); }
  public void delete(UUID id) { categories.deleteById(id); }
  private CategoryDto dto(Category category) { return new CategoryDto(category.id(),category.name(),category.slug(),category.icon(),category.itemCount()); }
}
