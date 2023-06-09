package com.UAC.ecommerce.infrastructure.adapter;

import com.UAC.ecommerce.application.repository.OrderRepository;
import com.UAC.ecommerce.domain.Order;
import com.UAC.ecommerce.domain.User;
import com.UAC.ecommerce.infrastructure.entity.OrderEntity;
import com.UAC.ecommerce.infrastructure.mapper.OrderMapper;
import com.UAC.ecommerce.infrastructure.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderCrudRepository orderCrudRepository;
    private final OrderMapper orderMapper;

    private final UserMapper userMapper;

    public OrderRepositoryImpl(OrderCrudRepository orderCrudRepository, OrderMapper orderMapper, UserMapper userMapper) {
        this.orderCrudRepository = orderCrudRepository;
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Order createOrder(Order order) {
        return orderMapper.toOrder(orderCrudRepository.save(orderMapper.toOrderEntity(order)));
    }

    @Override
    public Iterable<Order> getOrders() {
        return orderMapper.toOrders(orderCrudRepository.findAll());
    }

    @Override
    public Page<Order> getOrdersByUser(User user, Pageable pageable) {
        return orderMapper.toOrdersPage(orderCrudRepository.findByUser(userMapper.toUserEntity(user),pageable));
    }

    @Override
    public Order getOrdersById(Long id) {
        Optional<OrderEntity> optionalOrderEntity = orderCrudRepository.findById(id);
        return optionalOrderEntity.map(orderMapper::toOrder).orElse(null);
    }



    @Override
    public void save(Order order) {
        orderCrudRepository.save(orderMapper.toOrderEntity(order));
    }

    @Override
    public Page<Order> getOrdersPage(Pageable pageable) {
        return orderMapper.toOrdersPage(orderCrudRepository.findAll(pageable));
    }

}
