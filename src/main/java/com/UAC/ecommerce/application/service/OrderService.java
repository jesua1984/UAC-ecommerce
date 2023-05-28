package com.UAC.ecommerce.application.service;

import com.UAC.ecommerce.application.repository.OrderRepository;
import com.UAC.ecommerce.domain.Order;
import com.UAC.ecommerce.domain.User;

import java.util.List;
import java.util.Optional;

public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order){
        return orderRepository.createOrder(order);
    }

    public Iterable<Order> getOrders(){
        return orderRepository.getOrders();
    }



    public Iterable<Order> getOrdersByUser(User user){
        return orderRepository.getOrdersByUser(user);
    }

    public Order getOrderById(Long id) {
        return orderRepository.getOrdersById(id);
    }

    public void updateOrder(Order order) {
        orderRepository.save(order);
    }
}
