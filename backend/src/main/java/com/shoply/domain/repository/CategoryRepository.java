package com.shoply.domain.repository;

import com.shoply.domain.model.Category;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository { List<Category> findAll(); Optional<Category> findById(UUID id); }
