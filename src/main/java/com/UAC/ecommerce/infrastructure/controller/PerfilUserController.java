package com.UAC.ecommerce.infrastructure.controller;


import com.UAC.ecommerce.application.service.UserService;
import com.UAC.ecommerce.domain.User;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
@Slf4j
public class PerfilUserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public PerfilUserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
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

    @PostMapping("/save-user")
    public String createUser(User user, RedirectAttributes redirectAttributes, Model model) {
        log.info("Nombre del usuario: {}",user);
        userService.saveUser(user);

        model.addAttribute("mensaje", "Usuario guardado exitosamente.");
        model.addAttribute("clase", "success");

        return "user/edit";
    }

    @PostMapping("/change-password")
    public String savePassword(User user, RedirectAttributes redirectAttributes, Model model,HttpSession httpSession) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
            model.addAttribute("mensaje", "Usuario guardado exitosamente.");
            model.addAttribute("clase", "success");

        return "user/edit";
    }


}
