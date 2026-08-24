package com.shoply.application.service;

import com.shoply.application.dto.OrderDto;
import com.shoply.domain.repository.OrderRepository;
import java.util.List;
import java.util.UUID;
import com.shoply.domain.model.Order;
import com.shoply.domain.model.OrderStatus;

public class AdminOrderService {
  private final OrderRepository orders;
  public AdminOrderService(OrderRepository orders) { this.orders=orders; }
  public List<OrderDto> all() { return orders.findAll().stream().map(o->new OrderDto(o.id(),o.status().name(),o.items().stream().map(i->new com.shoply.application.dto.OrderItemDto(i.productId(),i.productName(),i.unitPrice(),i.quantity())).toList(),o.subtotal())).toList(); }
  public OrderDto updateStatus(UUID id, String status) { Order order=orders.findById(id).orElseThrow(()->new java.util.NoSuchElementException("Order not found")); Order updated=orders.save(new Order(order.id(),order.customerId(),OrderStatus.valueOf(status),order.items(),order.subtotal())); return new OrderDto(updated.id(),updated.status().name(),updated.items().stream().map(i->new com.shoply.application.dto.OrderItemDto(i.productId(),i.productName(),i.unitPrice(),i.quantity())).toList(),updated.subtotal()); }
}
