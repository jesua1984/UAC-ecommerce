package com.UAC.ecommerce.infrastructure.controller;

import com.UAC.ecommerce.application.service.UserService;
import com.UAC.ecommerce.domain.User;
import com.UAC.ecommerce.domain.UserType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@Controller
@RequestMapping("/admin/users")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create")
    public String create(){
        return "/admin/users/create";
    }

    @PostMapping("/save-product")
    public String createUser(User user) throws IOException {
        log.info("Nombre del usuario: {}",user);
        userService.saveUser(user);
        //return "admin/products/create";
        return "redirect:/admin/users/show";
    }
    @GetMapping("/show")
    public String showUser(Model model) {
        Iterable<User> users = userService.getUsersByType(UserType.USER);
        model.addAttribute("users", users);
        return "/admin/users/show";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model){
        User user = userService.getUserById(id);
        log.info("usuario obtenido: {}",user);
        model.addAttribute("user", user);
        return "/admin/users/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        return "redirect:/admin/users/show";
    }
}
