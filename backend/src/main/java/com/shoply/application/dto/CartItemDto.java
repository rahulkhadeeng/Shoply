package com.shoply.application.dto;
import java.util.UUID;
public record CartItemDto(UUID productId, String productName, int quantity) {}
