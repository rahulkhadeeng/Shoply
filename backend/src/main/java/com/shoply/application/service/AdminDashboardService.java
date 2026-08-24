package com.shoply.application.service;

import com.shoply.application.dto.AdminDashboardDto;
import com.shoply.domain.repository.AdminDashboardRepository;

public class AdminDashboardService {
  private final AdminDashboardRepository dashboard;
  public AdminDashboardService(AdminDashboardRepository dashboard) { this.dashboard=dashboard; }
  public AdminDashboardDto summary() { return new AdminDashboardDto(dashboard.productCount(),dashboard.categoryCount(),dashboard.customerCount(),dashboard.orderCount(),dashboard.lowInventoryCount(5)); }
}
