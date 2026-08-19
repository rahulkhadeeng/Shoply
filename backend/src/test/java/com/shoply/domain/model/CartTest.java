package com.shoply.domain.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
class CartTest { @Test void combinesSameProductIntoOneLine(){UUID product=UUID.randomUUID();Cart cart=new Cart(UUID.randomUUID(),UUID.randomUUID(),List.of());cart.add(product,1);cart.add(product,2);assertEquals(3,cart.items().getFirst().quantity());} }
