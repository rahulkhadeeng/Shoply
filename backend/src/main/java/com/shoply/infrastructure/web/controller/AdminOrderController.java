package com.shoply.infrastructure.web.controller;

import com.shoply.application.dto.OrderDto;
import com.shoply.application.service.AdminOrderService;
import java.util.List;
import java.util.UUID;
import com.shoply.infrastructure.web.request.OrderStatusRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
  private final AdminOrderService orders;
  public AdminOrderController(AdminOrderService orders){this.orders=orders;}
  @GetMapping public List<OrderDto> all(){return orders.all();}
  @PutMapping("/{id}/status") public OrderDto updateStatus(@PathVariable UUID id,@Valid @RequestBody OrderStatusRequest request){return orders.updateStatus(id,request.status());}
}
