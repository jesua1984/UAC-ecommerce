package com.UAC.ecommerce.application.service;

import com.UAC.ecommerce.application.repository.UserRepository;
import com.UAC.ecommerce.domain.Product;
import com.UAC.ecommerce.domain.User;
import com.UAC.ecommerce.domain.UserType;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.createUser(user);

    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findById(Long id) {
        return userRepository.findById(id);
    }

    public Iterable<User> getUsers() {
        return userRepository.getUsers();
    }

    public void saveUser(User user) {
    }

    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    public void deleteUserById(Long id) {
    }

    public Iterable<User> getUsersByType(UserType userType) {
        return userRepository.findByUserType(userType);
    }
}
