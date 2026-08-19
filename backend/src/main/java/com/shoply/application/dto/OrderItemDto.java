package com.shoply.application.dto;
import java.math.BigDecimal; import java.util.UUID;
public record OrderItemDto(UUID productId, String productName, BigDecimal unitPrice, int quantity) {}
