package com.UAC.ecommerce.application.service;

import com.UAC.ecommerce.domain.User;
import com.UAC.ecommerce.domain.UserType;
import com.UAC.ecommerce.infrastructure.dto.UserDto;

public class LoginService {
    private final UserService userService;

    public LoginService(UserService userService) {
        this.userService = userService;
    }

    //retorna true si encuentra el usuario
    public boolean existUser(UserDto userDto){
        try {
            User user = userService.findByEmail(userDto.getEmail());
        }catch (Exception e){
            return false;
        }
        return true;
    }

    //obtenemos el id del usuario
    public Long getUserId(String email){
        try{
            return userService.findByEmail(email).getId();

        }catch (Exception e){
            return null;
        }
    }
    //obtenemos el tipo de usuario
    public UserType getUserType(UserDto userDto){
        return userService.findByEmail(userDto.getEmail()).getUserType();
    }

    //obtenemos el usuario por email
    public User getUser(String email){
        try{
            return userService.findByEmail(email);

        }catch (Exception e){
            return new User();

        }
    }
}
