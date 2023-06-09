package com.UAC.ecommerce.infrastructure.controller;

import com.UAC.ecommerce.application.repository.UserRepository;
import com.UAC.ecommerce.application.service.UserService;

import com.UAC.ecommerce.domain.User;
import com.UAC.ecommerce.domain.UserType;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/users")
@Slf4j
public class UserController {
    private final UserService userService;

    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/create")
    public String create(){
        return "/admin/users/create";
    }

    @PostMapping("/save-user")
    public String createUser(User user, RedirectAttributes redirectAttributes) {
        log.info("Nombre del usuario: {}",user);
        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("mensaje", "Usuario actualizado correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:/admin/users/show";
    }
    @GetMapping("/show")
    public String showUser(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "2") int size,
                           Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userService.getUsersByType(UserType.USER, pageable);
        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());

        return "/admin/users/show";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model){
        User user = userService.getUserById(id);
        log.info("usuario obtenido: {}",user);
        model.addAttribute("user", user);
        return "/admin/users/edit";
    }

    @GetMapping("/update")
    public String updateUser(Model model, HttpSession httpSession){
        User user = new User();
        user.setId(Long.valueOf(httpSession.getAttribute("iduser").toString()));
        Long id = user.getId();
        user = userService.getUserById(id);
        log.info("usuario obtenido: {}",user);
        model.addAttribute("user", user);
        return "/user/edit";
    }



    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes attributes ){

        try {
            User user = userService.getUserById(id);
            user.setUserStatus("INACTIVO");
            userService.saveUser(user);
            //userService.deleteUserById(id);
            attributes.addFlashAttribute("success", "Usuario dado de baja correctamente");
            return "redirect:/admin/users/show";

        } catch (Exception e) {

            attributes.addFlashAttribute("success", "Imposible dar de baja al usuario");
            return "redirect:/admin/users/show";

        }
    }
}
