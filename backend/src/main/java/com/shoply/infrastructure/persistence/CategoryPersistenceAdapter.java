package com.shoply.infrastructure.persistence;
import com.shoply.domain.model.Category;
import com.shoply.domain.repository.CategoryRepository;
import com.shoply.infrastructure.persistence.mapper.CatalogPersistenceMapper;
import com.shoply.infrastructure.persistence.repository.SpringDataCategoryRepository;
import java.util.*;
import org.springframework.stereotype.Repository;
@Repository public class CategoryPersistenceAdapter implements CategoryRepository {
  private final SpringDataCategoryRepository categories; private final CatalogPersistenceMapper mapper;
  public CategoryPersistenceAdapter(SpringDataCategoryRepository categories, CatalogPersistenceMapper mapper){this.categories=categories;this.mapper=mapper;}
  public List<Category> findAll(){return categories.findAll().stream().map(mapper::category).toList();}
  public Optional<Category> findById(UUID id){return categories.findById(id).map(mapper::category);}
}
