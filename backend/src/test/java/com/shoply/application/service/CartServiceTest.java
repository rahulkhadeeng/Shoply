package com.shoply.application.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.shoply.domain.model.*;
import com.shoply.domain.repository.*;
import java.math.BigDecimal;
import java.util.*;
import org.junit.jupiter.api.Test;
class CartServiceTest { @Test void addsProductToCustomerCart(){UUID customer=UUID.randomUUID(), productId=UUID.randomUUID(); Product product=new Product(productId,"Lamp","lamp","desc",BigDecimal.ONE,null,BigDecimal.valueOf(4),"image",false,new Category(UUID.randomUUID(),"Home","home","House",1));CartRepository carts=new CartRepository(){Cart c;public Optional<Cart> findByCustomerId(UUID id){return Optional.ofNullable(c);}public Cart save(Cart cart){return c=cart;}};ProductRepository products=new ProductRepository(){public List<Product> findAll(){return List.of(product);}public List<Product> findFeatured(){return List.of();}public List<Product> searchByName(String q){return List.of();}public Optional<Product> findById(UUID id){return Optional.of(product);}};assertEquals(2,new CartService(carts,products).add(customer,productId,2).items().getFirst().quantity());} }
