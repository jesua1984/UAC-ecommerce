package com.UAC.ecommerce.infrastructure.adapter;

import com.UAC.ecommerce.infrastructure.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface OrderCrudRepository extends CrudRepository<OrderEntity , Long> {
    public Page<OrderEntity> findByUser(UserEntity userEntity, Pageable pageable);

    Page<OrderEntity> findAll(Pageable pageable);

}

