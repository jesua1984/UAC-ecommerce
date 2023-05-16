package com.UAC.ecommerce.infrastructure.service;

import com.UAC.ecommerce.application.service.LoginService;
import com.UAC.ecommerce.domain.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final LoginService loginService;
    private final Long USER_NOT_FOUND = null;

    @Autowired
    private HttpSession httpSession;

    public UserDetailServiceImpl(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Long idUser= loginService.getUserId(username);
        if (idUser != USER_NOT_FOUND){
            User user = loginService.getUser(username);
            httpSession.setAttribute("iduser", user.getId());
            return org.springframework.security.core.userdetails.User.builder().username(user.getUsername()).password(user.getPassword()).roles(user.getUserType().name()).build();
        }else{
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

    }
}
