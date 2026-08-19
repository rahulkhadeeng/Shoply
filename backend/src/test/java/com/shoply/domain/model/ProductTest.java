package com.shoply.domain.model;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
class ProductTest {
 @Test void rejectsNegativePrice(){ assertThrows(IllegalArgumentException.class, () -> new Product(null,"Name","name","desc",new BigDecimal("-1"),null,new BigDecimal("4.0"),"image",false,null)); }
}
