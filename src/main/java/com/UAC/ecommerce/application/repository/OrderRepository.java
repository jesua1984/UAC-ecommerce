    package com.UAC.ecommerce.application.repository;

    import com.UAC.ecommerce.domain.Order;
    import com.UAC.ecommerce.domain.User;
    import com.UAC.ecommerce.infrastructure.entity.UserEntity;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;

    import java.util.List;
    import java.util.Optional;

    public interface OrderRepository {
        public Order createOrder(Order order);
        public Iterable<Order> getOrders();

        public Page<Order> getOrdersByUser(User user, Pageable pageable);

        Order getOrdersById(Long id);

        void save(Order order);

        Page<Order> getOrdersPage(Pageable pageable);

    }
