package com.shoply.infrastructure.persistence.mapper;
import com.shoply.domain.model.*;
import com.shoply.infrastructure.persistence.entity.*;
import org.springframework.stereotype.Component;
@Component public class CatalogPersistenceMapper {
  public Category category(CategoryEntity e) { return new Category(e.getId(),e.getName(),e.getSlug(),e.getIcon(),e.getItemCount()); }
  public Product product(ProductEntity e) { return new Product(e.getId(),e.getName(),e.getSlug(),e.getDescription(),e.getPrice(),e.getPreviousPrice(),e.getRating(),e.getImageUrl(),e.isFeatured(),category(e.getCategory())); }
}
