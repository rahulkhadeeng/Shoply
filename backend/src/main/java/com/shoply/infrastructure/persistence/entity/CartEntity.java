package com.shoply.infrastructure.persistence.entity;
import jakarta.persistence.*;
import java.util.*;
@Entity @Table(name="carts") public class CartEntity { @Id private UUID id; @Column(name="user_id",nullable=false,unique=true) private UUID userId; @OneToMany(mappedBy="cart",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.EAGER) private List<CartItemEntity> items=new ArrayList<>(); protected CartEntity(){} public CartEntity(UUID id,UUID userId){this.id=id;this.userId=userId;} public UUID getId(){return id;} public UUID getUserId(){return userId;} public List<CartItemEntity> getItems(){return items;} }
