package com.shoply.infrastructure.persistence;
import com.shoply.domain.model.*;
import com.shoply.domain.repository.*;
import com.shoply.infrastructure.persistence.mapper.CatalogPersistenceMapper;
import com.shoply.infrastructure.persistence.repository.*;
import java.util.*;
import org.springframework.stereotype.Repository;

@Repository
public class CatalogPersistenceAdapter implements ProductRepository {
  private final SpringDataProductRepository products; private final SpringDataCategoryRepository categories; private final CatalogPersistenceMapper mapper;
  public CatalogPersistenceAdapter(SpringDataProductRepository products, SpringDataCategoryRepository categories, CatalogPersistenceMapper mapper) {this.products=products;this.categories=categories;this.mapper=mapper;}
  public List<Product> findAll(){return products.findAll().stream().map(mapper::product).toList();}
  public List<Product> findFeatured(){return products.findByFeaturedTrue().stream().map(mapper::product).toList();}
  public List<Product> searchByName(String q){return products.findByNameContainingIgnoreCase(q).stream().map(mapper::product).toList();}
  public Optional<Product> findById(UUID id){return products.findById(id).map(mapper::product);}
}
