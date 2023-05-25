package com.UAC.ecommerce.infrastructure.adapter;

import com.UAC.ecommerce.domain.UserType;
import com.UAC.ecommerce.infrastructure.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserCrudRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByUserType(UserType userType);


}
