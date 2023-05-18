package com.UAC.ecommerce.application.repository;

import com.UAC.ecommerce.domain.Order;
import com.UAC.ecommerce.domain.User;

public interface OrderRepository {
    public Order createOrder(Order order);
    public Iterable<Order> getOrders();

    public Iterable<Order> getOrdersByUser(User user);


}
