package com.shoply.infrastructure.persistence;
import com.shoply.domain.model.Category;
import com.shoply.domain.repository.CategoryRepository;
import com.shoply.infrastructure.persistence.entity.CategoryEntity;
import com.shoply.infrastructure.persistence.repository.SpringDataCategoryRepository;
import com.shoply.infrastructure.persistence.repository.SpringDataProductRepository;
import java.util.*;
import org.springframework.stereotype.Repository;
@Repository public class CategoryPersistenceAdapter implements CategoryRepository {
  private final SpringDataCategoryRepository categories; private final SpringDataProductRepository products;
  public CategoryPersistenceAdapter(SpringDataCategoryRepository categories, SpringDataProductRepository products){this.categories=categories;this.products=products;}
  public List<Category> findAll(){return categories.findAll().stream().map(this::category).toList();}
  public Optional<Category> findById(UUID id){return categories.findById(id).map(this::category);}
  private Category category(CategoryEntity entity){return new Category(entity.getId(), entity.getName(), entity.getSlug(), entity.getIcon(), Math.toIntExact(products.countByCategoryId(entity.getId())));}
}
