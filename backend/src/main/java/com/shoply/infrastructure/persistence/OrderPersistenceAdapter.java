package com.shoply.infrastructure.persistence;

import com.shoply.domain.model.*;
import com.shoply.domain.repository.OrderRepository;
import com.shoply.infrastructure.persistence.entity.*;
import com.shoply.infrastructure.persistence.repository.SpringDataOrderRepository;
import java.util.*;
import org.springframework.stereotype.Repository;

@Repository
public class OrderPersistenceAdapter implements OrderRepository {
  private final SpringDataOrderRepository orders;
  public OrderPersistenceAdapter(SpringDataOrderRepository orders) { this.orders=orders; }
  public Order save(Order order) { var existing=orders.findById(order.id()); if(existing.isPresent()){existing.get().setStatus(order.status().name());return domain(orders.save(existing.get()));} OrderEntity entity=new OrderEntity(order.id(),order.customerId(),order.status().name(),order.subtotal()); order.items().forEach(i->entity.getItems().add(new OrderItemEntity(UUID.randomUUID(),entity,i.productId(),i.productName(),i.unitPrice(),i.quantity()))); return domain(orders.save(entity)); }
  public Optional<Order> findByIdAndCustomerId(UUID id,UUID customerId) { return orders.findByIdAndUserId(id,customerId).map(this::domain); }
  public List<Order> findByCustomerId(UUID customerId) { return orders.findByUserIdOrderByCreatedAtDesc(customerId).stream().map(this::domain).toList(); }
  public List<Order> findAll() { return orders.findAll().stream().map(this::domain).toList(); }
  public Optional<Order> findById(UUID id) { return orders.findById(id).map(this::domain); }
  private Order domain(OrderEntity e) { return new Order(e.getId(),e.getUserId(),OrderStatus.valueOf(e.getStatus()),e.getItems().stream().map(i->new OrderItem(i.getProductId(),i.getProductName(),i.getUnitPrice(),i.getQuantity())).toList(),e.getSubtotal()); }
}
