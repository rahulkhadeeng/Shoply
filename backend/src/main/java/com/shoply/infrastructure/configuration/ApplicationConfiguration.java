package com.shoply.infrastructure.configuration;

import com.shoply.application.service.CatalogQueryService;
import com.shoply.application.service.CartService;
import com.shoply.application.service.AuthService;
import com.shoply.application.service.OrderService;
import com.shoply.application.port.PasswordHasher;
import com.shoply.application.port.TokenIssuer;
import com.shoply.domain.repository.CategoryRepository;
import com.shoply.domain.repository.CartRepository;
import com.shoply.domain.repository.ProductRepository;
import com.shoply.domain.repository.UserRepository;
import com.shoply.domain.repository.OrderRepository;
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
}
