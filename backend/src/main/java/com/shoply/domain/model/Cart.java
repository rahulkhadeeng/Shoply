package com.shoply.domain.model;

import java.util.*;

public final class Cart {
  private final UUID id; private final UUID customerId; private final List<CartItem> items;
  public Cart(UUID id, UUID customerId, List<CartItem> items) { this.id=id; this.customerId=customerId; this.items=new ArrayList<>(items); }
  public UUID id(){return id;} public UUID customerId(){return customerId;} public List<CartItem> items(){return List.copyOf(items);}
  public void add(UUID productId, int quantity){items.stream().filter(i->i.productId().equals(productId)).findFirst().ifPresentOrElse(i->{items.set(items.indexOf(i),i.changeQuantity(i.quantity()+quantity));},()->items.add(new CartItem(productId,quantity)));}
  public void remove(UUID productId){items.removeIf(i->i.productId().equals(productId));}
  public void clear(){items.clear();}
}
