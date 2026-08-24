package com.shoply.infrastructure.persistence;

import com.shoply.domain.repository.AdminDashboardRepository;
import com.shoply.infrastructure.persistence.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDashboardPersistenceAdapter implements AdminDashboardRepository {
  private final SpringDataProductRepository products; private final SpringDataCategoryRepository categories; private final SpringDataUserRepository users; private final SpringDataOrderRepository orders; private final SpringDataInventoryRepository inventory;
  public AdminDashboardPersistenceAdapter(SpringDataProductRepository products,SpringDataCategoryRepository categories,SpringDataUserRepository users,SpringDataOrderRepository orders,SpringDataInventoryRepository inventory){this.products=products;this.categories=categories;this.users=users;this.orders=orders;this.inventory=inventory;}
  public long productCount(){return products.count();} public long categoryCount(){return categories.count();} public long customerCount(){return users.count();} public long orderCount(){return orders.count();} public long lowInventoryCount(int threshold){return inventory.countByQuantityLessThanEqual(threshold);}
}
