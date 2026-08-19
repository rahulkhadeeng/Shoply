package com.shoply.infrastructure.web.controller;

import com.shoply.application.dto.OrderDto;
import com.shoply.application.service.OrderService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  private final OrderService orders;
  public OrderController(OrderService orders) { this.orders = orders; }
  @PostMapping public ResponseEntity<OrderDto> place(Authentication auth) { return ResponseEntity.status(HttpStatus.CREATED).body(orders.place(user(auth))); }
  @GetMapping public List<OrderDto> history(Authentication auth) { return orders.history(user(auth)); }
  @GetMapping("/{id}") public OrderDto get(Authentication auth, @PathVariable UUID id) { return orders.get(user(auth), id); }
  private UUID user(Authentication auth) { return UUID.fromString(auth.getName()); }
}
