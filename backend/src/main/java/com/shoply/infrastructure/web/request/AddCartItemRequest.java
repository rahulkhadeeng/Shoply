package com.shoply.infrastructure.web.request;
import jakarta.validation.constraints.*; import java.util.UUID;
public record AddCartItemRequest(@NotNull UUID productId,@Min(1) @Max(99) int quantity) {}
