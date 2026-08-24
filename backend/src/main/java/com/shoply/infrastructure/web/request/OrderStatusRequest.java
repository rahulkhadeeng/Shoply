package com.shoply.infrastructure.web.request;
import jakarta.validation.constraints.NotBlank;
public record OrderStatusRequest(@NotBlank String status) {}
