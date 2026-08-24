package com.shoply.domain.repository;

import com.shoply.domain.model.Product;

public interface ProductManagementRepository {
  Product save(Product product, int inventoryQuantity);
  void deleteById(java.util.UUID productId);
}
