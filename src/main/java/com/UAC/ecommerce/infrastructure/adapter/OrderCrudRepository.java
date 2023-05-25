package com.UAC.ecommerce.infrastructure.adapter;

import com.UAC.ecommerce.infrastructure.entity.OrderEntity;
import com.UAC.ecommerce.infrastructure.entity.ProductEntity;
import com.UAC.ecommerce.infrastructure.entity.StockEntity;
import com.UAC.ecommerce.infrastructure.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderCrudRepository extends CrudRepository<OrderEntity , Long> {
    public Iterable<OrderEntity> findByUser(UserEntity userEntity);

}
