package com.shoply.domain.repository;

public interface AdminDashboardRepository {
  long productCount();
  long categoryCount();
  long customerCount();
  long orderCount();
  long lowInventoryCount(int threshold);
}
