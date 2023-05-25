    package com.UAC.ecommerce.application.repository;

    import com.UAC.ecommerce.domain.Order;
    import com.UAC.ecommerce.domain.User;

    import java.util.List;
    import java.util.Optional;

    public interface OrderRepository {
        public Order createOrder(Order order);
        public Iterable<Order> getOrders();

        public Iterable<Order> getOrdersByUser(User user);

    }
