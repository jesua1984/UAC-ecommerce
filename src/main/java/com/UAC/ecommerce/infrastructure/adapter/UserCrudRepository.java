package com.UAC.ecommerce.infrastructure.adapter;

import com.UAC.ecommerce.domain.User;
import com.UAC.ecommerce.domain.UserType;
import com.UAC.ecommerce.infrastructure.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserCrudRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);



    Page<UserEntity> findByUserType(UserType userType, Pageable pageable);


}
