package com.shoply.domain.repository;
import com.shoply.domain.model.Order;
import java.util.*;
public interface OrderRepository { Order save(Order order); Optional<Order> findByIdAndCustomerId(UUID id, UUID customerId); List<Order> findByCustomerId(UUID customerId); }
