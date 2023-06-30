package com.UAC.ecommerce.infrastructure.adapter;

import com.UAC.ecommerce.domain.Product;
import com.UAC.ecommerce.infrastructure.entity.CategoryEntity;
import com.UAC.ecommerce.infrastructure.entity.ProductEntity;
import com.UAC.ecommerce.infrastructure.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductCrudRepository extends CrudRepository<ProductEntity,Long> {
    Page<ProductEntity>findByUserEntity(UserEntity userEntity, Pageable pageable);

    Page<ProductEntity> findByCategoryEntity(CategoryEntity categoryEntity, Pageable pageable);


    Page<Product> findAll(Pageable pageable);


    List<ProductEntity> findByNameContainingIgnoreCase(String keyword);

}
