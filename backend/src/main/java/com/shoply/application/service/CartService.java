package com.shoply.application.service;

import com.shoply.application.dto.*;
import com.shoply.domain.model.*;
import com.shoply.domain.repository.*;
import java.util.*;

public class CartService {
  private final CartRepository carts; private final ProductRepository products;
  public CartService(CartRepository carts, ProductRepository products){this.carts=carts;this.products=products;}
  public CartDto get(UUID customerId){return dto(load(customerId));}
  public CartDto add(UUID customerId, UUID productId, int quantity){if(products.findById(productId).isEmpty())throw new NoSuchElementException("Product not found"); Cart cart=load(customerId);cart.add(productId,quantity);return dto(carts.save(cart));}
  public CartDto remove(UUID customerId, UUID productId){Cart cart=load(customerId);cart.remove(productId);return dto(carts.save(cart));}
  public CartDto update(UUID customerId, UUID productId, int quantity){Cart cart=load(customerId);if(cart.items().stream().noneMatch(i->i.productId().equals(productId)))throw new NoSuchElementException("Cart item not found");cart.items().stream().filter(i->i.productId().equals(productId)).findFirst().ifPresent(i->{cart.remove(productId);cart.add(productId,quantity);});return dto(carts.save(cart));}
  private Cart load(UUID customerId){return carts.findByCustomerId(customerId).orElseGet(()->new Cart(UUID.randomUUID(),customerId,List.of()));}
  private CartDto dto(Cart cart){return new CartDto(cart.id(),cart.items().stream().map(i->{var p=products.findById(i.productId()).orElseThrow(()->new NoSuchElementException("Product not found"));return new CartItemDto(p.id(),p.name(),i.quantity());}).toList());}
}
