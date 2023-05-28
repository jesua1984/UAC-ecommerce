package com.UAC.ecommerce.application.repository;

import com.UAC.ecommerce.domain.Product;
import com.UAC.ecommerce.domain.User;
import com.UAC.ecommerce.domain.UserType;

public interface UserRepository {
    public User createUser(User user);

    public User findByEmail(String email);
    public User findById(Long id);

    Iterable<User> getUsers();

    User getUserById(Long id);

    Iterable<User> findByUserType(UserType userType);

    User saveUser(User user);

    void deleteUserById(Long id);

}
