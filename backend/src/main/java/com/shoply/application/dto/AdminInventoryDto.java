package com.shoply.application.dto;
import java.util.UUID;
public record AdminInventoryDto(UUID productId, String productName, String categoryName, int quantity) {}
