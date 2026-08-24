package com.shoply.infrastructure.persistence;

import com.shoply.domain.model.Category;
import com.shoply.domain.repository.CategoryManagementRepository;
import com.shoply.infrastructure.persistence.entity.CategoryEntity;
import com.shoply.infrastructure.persistence.repository.SpringDataCategoryRepository;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryManagementPersistenceAdapter implements CategoryManagementRepository {
  private final SpringDataCategoryRepository categories;
  public CategoryManagementPersistenceAdapter(SpringDataCategoryRepository categories) { this.categories=categories; }
  public Category save(Category category) {
    var saved=categories.save(new CategoryEntity(category.id(),category.name(),category.slug(),category.icon(),category.itemCount()));
    return new Category(saved.getId(),saved.getName(),saved.getSlug(),saved.getIcon(),saved.getItemCount());
  }
  public void deleteById(UUID id) { categories.deleteById(id); }
}
