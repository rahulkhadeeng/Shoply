package com.shoply.domain.model;
import java.math.BigDecimal;
import java.util.*;
public record Order(UUID id, UUID customerId, OrderStatus status, List<OrderItem> items, BigDecimal subtotal) { public Order { items=List.copyOf(items); if(items.isEmpty())throw new IllegalArgumentException("Order must contain items"); } }
