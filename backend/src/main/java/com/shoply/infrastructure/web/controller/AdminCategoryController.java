package com.shoply.infrastructure.web.controller;

import com.shoply.application.dto.*;
import com.shoply.application.service.CategoryManagementService;
import com.shoply.infrastructure.web.request.CategoryRequest;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/categories")
public class AdminCategoryController {
  private final CategoryManagementService categories;
  public AdminCategoryController(CategoryManagementService categories) { this.categories=categories; }
  @PostMapping public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryRequest request) { return ResponseEntity.status(HttpStatus.CREATED).body(categories.create(new CategoryCommand(request.name(),request.slug(),request.icon()))); }
  @PutMapping("/{id}") public CategoryDto update(@PathVariable UUID id,@Valid @RequestBody CategoryRequest request) { return categories.update(id,new CategoryCommand(request.name(),request.slug(),request.icon())); }
  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable UUID id) { categories.delete(id); }
}
