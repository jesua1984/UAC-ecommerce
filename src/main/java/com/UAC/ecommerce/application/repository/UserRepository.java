package com.UAC.ecommerce.application.repository;

import com.UAC.ecommerce.domain.User;
import com.UAC.ecommerce.domain.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository {
     User createUser(User user);

     User findByEmail(String email);

     User findById(Long id);

    Iterable<User> getUsers();

    User getUserById(Long id);

    Page<User> findByUserType(UserType userType, Pageable pageable);

    User saveUser(User user);

    void deleteUserById(Long id);

}
