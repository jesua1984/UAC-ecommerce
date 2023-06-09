package com.UAC.ecommerce.application.service;

import com.UAC.ecommerce.application.repository.OrderRepository;
import com.UAC.ecommerce.domain.Order;
import com.UAC.ecommerce.domain.User;
import com.UAC.ecommerce.infrastructure.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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



    public Page<Order> getOrdersByUser(User user, Pageable pageable){
        return orderRepository.getOrdersByUser(user, pageable);
    }

    public Order getOrderById(Long id) {
        return orderRepository.getOrdersById(id);
    }

    public void updateOrder(Order order) {
        orderRepository.save(order);
    }

    public Page<Order> getOrdersPage(Pageable pageable){
        return orderRepository.getOrdersPage(pageable);
    }

}
