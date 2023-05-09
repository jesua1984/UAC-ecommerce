package com.UAC.ecommerce.application.repository;

import com.UAC.ecommerce.domain.Order;

public interface OrderRepository {
    public Order createOrder(Order order);
    public Iterable<Order> getOrders();

}
