package com.shoply.domain.repository;
import com.shoply.domain.model.Cart;
import java.util.Optional;
import java.util.UUID;
public interface CartRepository { Optional<Cart> findByCustomerId(UUID customerId); Cart save(Cart cart); }
