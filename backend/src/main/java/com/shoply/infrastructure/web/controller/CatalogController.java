package com.shoply.infrastructure.web.controller;
import com.shoply.application.dto.*;
import com.shoply.application.service.CatalogQueryService;
import java.util.*;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api") @CrossOrigin(origins = "${shoply.cors-origin:http://localhost:5173}")
public class CatalogController {
  private final CatalogQueryService catalog; public CatalogController(CatalogQueryService catalog){this.catalog=catalog;}
  @GetMapping("/products") public List<ProductDto> products(){return catalog.products();}
  @GetMapping("/products/featured") public List<ProductDto> featured(){return catalog.featured();}
  @GetMapping("/products/search") public List<ProductDto> search(@RequestParam(defaultValue="") String q){return catalog.search(q);}
  @GetMapping("/products/{id}") public ProductDto product(@PathVariable UUID id){return catalog.productById(id);}
  @GetMapping("/categories") public List<CategoryDto> categories(){return catalog.categories();}
  @GetMapping("/categories/{id}") public CategoryDto category(@PathVariable UUID id){return catalog.categoryById(id);}
}
