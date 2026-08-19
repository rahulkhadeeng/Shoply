package com.shoply.domain.model;
import java.math.BigDecimal;
import java.util.UUID;
public record OrderItem(UUID productId, String productName, BigDecimal unitPrice, int quantity) { public OrderItem { if(quantity<1)throw new IllegalArgumentException("Quantity must be at least one"); } public BigDecimal lineTotal(){return unitPrice.multiply(BigDecimal.valueOf(quantity));} }
