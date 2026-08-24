package com.shoply.infrastructure.persistence;

import com.shoply.domain.model.Product;
import com.shoply.domain.repository.ProductManagementRepository;
import com.shoply.infrastructure.persistence.entity.InventoryEntity;
import com.shoply.infrastructure.persistence.entity.ProductEntity;
import com.shoply.infrastructure.persistence.mapper.CatalogPersistenceMapper;
import com.shoply.infrastructure.persistence.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ProductManagementPersistenceAdapter implements ProductManagementRepository {
  private final SpringDataProductRepository products;
  private final SpringDataCategoryRepository categories;
  private final SpringDataInventoryRepository inventory;
  private final CatalogPersistenceMapper mapper;
  public ProductManagementPersistenceAdapter(SpringDataProductRepository products, SpringDataCategoryRepository categories, SpringDataInventoryRepository inventory, CatalogPersistenceMapper mapper) { this.products=products;this.categories=categories;this.inventory=inventory;this.mapper=mapper; }
  @Transactional public Product save(Product product, int inventoryQuantity) {
    var category=categories.findById(product.category().id()).orElseThrow();
    var entity=products.save(new ProductEntity(product.id(),product.name(),product.slug(),product.description(),product.price(),product.previousPrice(),product.rating(),product.imageUrl(),product.featured(),category));
    inventory.save(new InventoryEntity(entity.getId(),inventoryQuantity));
    return mapper.product(entity);
  }
  @Transactional public void deleteById(java.util.UUID productId) { inventory.deleteById(productId); products.deleteById(productId); }
}
