package com.shoply.infrastructure.configuration;

import com.shoply.application.service.CatalogQueryService;
import com.shoply.application.service.CartService;
import com.shoply.application.service.AuthService;
import com.shoply.application.service.OrderService;
import com.shoply.application.service.ProductManagementService;
import com.shoply.application.service.CategoryManagementService;
import com.shoply.application.service.AdminDashboardService;
import com.shoply.application.service.AdminOrderService;
import com.shoply.application.service.AdminCustomerService;
import com.shoply.application.service.AdminInventoryService;
import com.shoply.infrastructure.persistence.repository.SpringDataUserRepository;
import com.shoply.infrastructure.persistence.repository.SpringDataInventoryRepository;
import com.shoply.application.port.PasswordHasher;
import com.shoply.application.port.TokenIssuer;
import com.shoply.domain.repository.CategoryRepository;
import com.shoply.domain.repository.CartRepository;
import com.shoply.domain.repository.ProductRepository;
import com.shoply.domain.repository.UserRepository;
import com.shoply.domain.repository.OrderRepository;
import com.shoply.domain.repository.ProductManagementRepository;
import com.shoply.domain.repository.CategoryManagementRepository;
import com.shoply.domain.repository.AdminDashboardRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
  @Bean
  CatalogQueryService catalogQueryService(ProductRepository products, CategoryRepository categories) {
    return new CatalogQueryService(products, categories);
  }
  @Bean
  CartService cartService(CartRepository carts, ProductRepository products) { return new CartService(carts, products); }
  @Bean
  AuthService authService(UserRepository users, PasswordHasher passwords, TokenIssuer tokens) { return new AuthService(users, passwords, tokens); }
  @Bean
  OrderService orderService(CartRepository carts, ProductRepository products, OrderRepository orders) { return new OrderService(carts, products, orders); }
  @Bean
  ProductManagementService productManagementService(ProductManagementRepository products, CategoryRepository categories) { return new ProductManagementService(products, categories); }
  @Bean
  CategoryManagementService categoryManagementService(CategoryManagementRepository categories) { return new CategoryManagementService(categories); }
  @Bean
  AdminDashboardService adminDashboardService(AdminDashboardRepository dashboard) { return new AdminDashboardService(dashboard); }
  @Bean
  AdminOrderService adminOrderService(OrderRepository orders) { return new AdminOrderService(orders); }
  @Bean
  AdminCustomerService adminCustomerService(SpringDataUserRepository users) { return new AdminCustomerService(users); }
  @Bean
  AdminInventoryService adminInventoryService(SpringDataInventoryRepository inventory, com.shoply.infrastructure.persistence.repository.SpringDataProductRepository products) { return new AdminInventoryService(inventory, products); }
}
