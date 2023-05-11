package com.UAC.ecommerce.application.service;

import com.UAC.ecommerce.domain.User;

public class RegistrationService {
    private final UserService userService;

    public RegistrationService(UserService userService) {
        this.userService = userService;
    }

    public void register(User user){
        userService.createUser(user);
    }
}
