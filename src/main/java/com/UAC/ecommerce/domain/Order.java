package com.UAC.ecommerce.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order {
    private Long id;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime dateCreated;
    private List<OrderProduct> orderProducts;
    private User user;

    private String orderStatus;

    public void addOrdersProduct(List<OrderProduct> orderProducts){
        this.setOrderProducts(orderProducts);
    }

    public BigDecimal getTotalOrderPrice(){
        return getOrderProducts().stream().map(
                p->p.getTotalPrice()
        ).reduce(BigDecimal.ZERO,BigDecimal::add);
    }

}
