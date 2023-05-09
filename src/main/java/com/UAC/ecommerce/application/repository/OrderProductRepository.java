package com.UAC.ecommerce.application.repository;

import com.UAC.ecommerce.domain.Order;
import com.UAC.ecommerce.domain.OrderProduct;

import java.util.List;

public interface OrderProductRepository {
    public OrderProduct create(OrderProduct orderProduct);
    public Iterable<OrderProduct> getOrderProducts();

    public List<OrderProduct> getOrdersProductByOrder(Order order);


}
