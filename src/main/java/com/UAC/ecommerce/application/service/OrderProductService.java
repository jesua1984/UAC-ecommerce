package com.UAC.ecommerce.application.service;

import com.UAC.ecommerce.application.repository.OrderProductRepository;
import com.UAC.ecommerce.domain.Order;
import com.UAC.ecommerce.domain.OrderProduct;

import java.util.List;

public class OrderProductService {
    private final OrderProductRepository orderProductRepository;

    public OrderProductService(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }
    public OrderProduct create(OrderProduct orderProduct){
        return orderProductRepository.create(orderProduct);

    }

    public Iterable<OrderProduct> getOrderProduct(){
        return orderProductRepository.getOrderProducts();
    }

    public List<OrderProduct> getOrderProductByOrder(Order order){
        return orderProductRepository.getOrdersProductByOrder(order);

    }
}
