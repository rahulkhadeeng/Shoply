package com.shoply.domain.repository;

import com.shoply.domain.model.Product;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository { List<Product> findAll(); List<Product> findFeatured(); List<Product> searchByName(String query); Optional<Product> findById(UUID id); }
