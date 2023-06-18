package com.UAC.ecommerce.infrastructure.adapter;

import com.UAC.ecommerce.domain.Product;
import com.UAC.ecommerce.infrastructure.entity.ProductEntity;
import com.UAC.ecommerce.infrastructure.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductCrudRepository extends CrudRepository<ProductEntity,Long> {
    Page<ProductEntity>findByUserEntity(UserEntity userEntity, Pageable pageable);


    Page<Product> findAll(Pageable pageable);


    List<ProductEntity> findByNameContainingIgnoreCase(String keyword);

    List<ProductEntity> findByCategoryEntity_NameContainingIgnoreCase(String category);

    @Query("SELECT p FROM ProductEntity p WHERE p.categoryEntity.name LIKE %:category%")
    List<ProductEntity> findByCategoryContainingIgnoreCase(@Param("category") String category);


}
