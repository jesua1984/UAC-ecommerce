package com.UAC.ecommerce.application.repository;

import com.UAC.ecommerce.domain.User;

public interface UserRepository {
    public User createUser(User user);
    public User findByEmail(String email);
    public User findById(Long id);
}
