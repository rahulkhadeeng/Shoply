package com.shoply.infrastructure.persistence.entity;
import java.io.Serializable; import java.util.UUID;
public class CartItemKey implements Serializable { private UUID cart; private UUID productId; public CartItemKey(){} }
