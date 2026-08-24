package com.shoply.infrastructure.web.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductRequest(@NotBlank @Size(max=200) String name, @NotBlank @Pattern(regexp="[a-z0-9]+(?:-[a-z0-9]+)*") String slug,
  @NotBlank @Size(max=1000) String description, @NotNull @DecimalMin("0.00") BigDecimal price, @DecimalMin("0.00") BigDecimal previousPrice,
  @NotNull @DecimalMin("0.0") @DecimalMax("5.0") BigDecimal rating, @NotBlank @Size(max=1000) String imageUrl, boolean featured,
  @NotNull UUID categoryId, @Min(0) int inventoryQuantity) {}
