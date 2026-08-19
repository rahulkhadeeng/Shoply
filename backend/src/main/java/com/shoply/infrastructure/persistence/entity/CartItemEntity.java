package com.shoply.infrastructure.persistence.entity;
import jakarta.persistence.*;
import java.util.UUID;
@Entity @Table(name="cart_items") @IdClass(CartItemKey.class) public class CartItemEntity { @Id @ManyToOne @JoinColumn(name="cart_id") private CartEntity cart; @Id @Column(name="product_id") private UUID productId; @Column(nullable=false) private int quantity; protected CartItemEntity(){} public CartItemEntity(CartEntity cart,UUID productId,int quantity){this.cart=cart;this.productId=productId;this.quantity=quantity;} public UUID getProductId(){return productId;} public int getQuantity(){return quantity;} }
