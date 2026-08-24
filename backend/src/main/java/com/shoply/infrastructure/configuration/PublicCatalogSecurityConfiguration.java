package com.shoply.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/** Public product browsing; administrative endpoints remain protected. */
@Configuration
public class PublicCatalogSecurityConfiguration {
  @Bean
  @Order(1)
  SecurityFilterChain publicCatalogFilterChain(HttpSecurity http) throws Exception {
    return http.securityMatcher("/api/products", "/api/products/**", "/api/categories", "/api/categories/**")
        .csrf(csrf -> csrf.disable())
        .cors(cors -> {})
        .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll()).build();
  }
}
