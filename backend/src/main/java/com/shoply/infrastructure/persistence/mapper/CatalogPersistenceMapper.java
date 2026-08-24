package com.shoply.infrastructure.persistence.mapper;
import com.shoply.domain.model.*;
import com.shoply.infrastructure.persistence.entity.*;
import org.springframework.stereotype.Component;
import com.shoply.infrastructure.persistence.repository.SpringDataProductRepository;
@Component public class CatalogPersistenceMapper {
  private final SpringDataProductRepository products;
  public CatalogPersistenceMapper(SpringDataProductRepository products) { this.products = products; }

  public Category category(CategoryEntity e) { return new Category(e.getId(),e.getName(),e.getSlug(),e.getIcon(),Math.toIntExact(products.countByCategoryId(e.getId()))); }
  public Product product(ProductEntity e) { return new Product(e.getId(),e.getName(),e.getSlug(),e.getDescription(),e.getPrice(),e.getPreviousPrice(),e.getRating(),e.getImageUrl(),e.getImages().isEmpty() ? java.util.List.of(e.getImageUrl()) : e.getImages().stream().map(ProductImageEntity::getImageUrl).toList(),e.isFeatured(),category(e.getCategory())); }
}
