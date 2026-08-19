package com.shoply.application.dto;
import java.util.List;
import java.util.UUID;
public record CartDto(UUID id, List<CartItemDto> items) {}
