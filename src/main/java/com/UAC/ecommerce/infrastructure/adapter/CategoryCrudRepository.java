package com.UAC.ecommerce.infrastructure.adapter;

import com.UAC.ecommerce.infrastructure.entity.CategoryEntity;
import com.UAC.ecommerce.infrastructure.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryCrudRepository extends CrudRepository<CategoryEntity, Long> {

    Page<CategoryEntity> findAll(Pageable pageable);


}
