package com.shoply.application.dto;
import java.math.BigDecimal; import java.util.*;
public record OrderDto(UUID id, String status, List<OrderItemDto> items, BigDecimal subtotal) {}
