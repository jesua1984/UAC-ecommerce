package com.UAC.ecommerce.infrastructure.controller;

import com.UAC.ecommerce.application.service.RegistrationService;
import com.UAC.ecommerce.application.service.UserService;
import com.UAC.ecommerce.domain.User;
import com.UAC.ecommerce.infrastructure.dto.UserDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/register")
@Slf4j
public class RegistrationController {

    private final RegistrationService registrationService;

    private final UserService userService;

    public RegistrationController(RegistrationService registrationService, UserService userService) {
        this.registrationService = registrationService;
        this.userService = userService;
    }

    @GetMapping
    public String register(UserDto userDto){

        return "register";
    }

    @PostMapping
    public String registerUser(@Valid UserDto userDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // Verificar si el email ya está registrado
        User existingUser = userService.findByEmail(userDto.getEmail());

        if (existingUser != null) {
            bindingResult.rejectValue("email", "error.email", "");
            redirectAttributes.addFlashAttribute("mensaje", "El email ya se encuentra registrado")
                    .addFlashAttribute("clase", "danger");
        }

        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "");
            redirectAttributes.addFlashAttribute("mensaje", "Las contraseñas no coinciden")
                    .addFlashAttribute("clase", "danger");
        }

        // Validar que la contraseña tenga al menos una letra mayúscula, un carácter especial, 5 letras y 2 números
        Pattern uppercasePattern = Pattern.compile(".*[A-Z].*");
        Pattern specialCharPattern = Pattern.compile(".*[@#$%&*.].*");
        Pattern letterPattern = Pattern.compile("[a-zA-Z]{5,}");
        Pattern numberPattern = Pattern.compile(".*\\d.*");

        if (!uppercasePattern.matcher(userDto.getPassword()).matches() ||
                !specialCharPattern.matcher(userDto.getPassword()).matches() ||
                !letterPattern.matcher(userDto.getPassword()).matches() ||
                !numberPattern.matcher(userDto.getPassword()).matches()) {
            bindingResult.rejectValue("password", "error.password", "");
            redirectAttributes.addFlashAttribute("mensaje", "Formato de Contraseña no permitido")
                    .addFlashAttribute("clase", "danger");
        }

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(e -> {
                log.info("Error {}", e.getDefaultMessage());
            });
            return "redirect:/register";
        }

        userDto.setUserStatus("ACTIVO");
        registrationService.register(userDto.userDtoToUser());
        redirectAttributes.addFlashAttribute("mensaje", "Usuario creado correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:/login";
    }

}
