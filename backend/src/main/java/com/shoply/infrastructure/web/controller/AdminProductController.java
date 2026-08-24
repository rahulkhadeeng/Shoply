package com.shoply.infrastructure.web.controller;

import com.shoply.application.dto.CreateProductCommand;
import com.shoply.application.dto.ProductDto;
import com.shoply.application.service.ProductManagementService;
import com.shoply.infrastructure.web.request.CreateProductRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {
  private final ProductManagementService products;
  public AdminProductController(ProductManagementService products) { this.products=products; }
  @PostMapping
  public ResponseEntity<ProductDto> create(@Valid @RequestBody CreateProductRequest request) {
    var command = new CreateProductCommand(request.name(),request.slug(),request.description(),request.price(),request.previousPrice(),request.rating(),request.imageUrl(),request.featured(),request.categoryId(),request.inventoryQuantity());
    return ResponseEntity.status(HttpStatus.CREATED).body(products.create(command));
  }
  @PutMapping("/{productId}")
  public ProductDto update(@PathVariable java.util.UUID productId, @Valid @RequestBody CreateProductRequest request) {
    return products.update(productId, new CreateProductCommand(request.name(),request.slug(),request.description(),request.price(),request.previousPrice(),request.rating(),request.imageUrl(),request.featured(),request.categoryId(),request.inventoryQuantity()));
  }
  @DeleteMapping("/{productId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable java.util.UUID productId) { products.delete(productId); }
}
