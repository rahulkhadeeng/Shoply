package com.shoply.domain.repository;

import com.shoply.domain.model.Category;
import java.util.UUID;

public interface CategoryManagementRepository {
  Category save(Category category);
  void deleteById(UUID categoryId);
}
