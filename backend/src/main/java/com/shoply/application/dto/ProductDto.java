package com.shoply.application.dto;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
public record ProductDto(UUID id, String name, String slug, String description, BigDecimal price, BigDecimal previousPrice, BigDecimal rating, String imageUrl, List<String> imageUrls, boolean featured, CategoryDto category) {}
