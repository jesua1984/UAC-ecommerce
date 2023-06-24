package com.UAC.ecommerce.infrastructure.adapter;

import com.UAC.ecommerce.infrastructure.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryCrudRepository extends CrudRepository<CategoryEntity, Long> {

    Page<CategoryEntity> findAll(Pageable pageable);


}
