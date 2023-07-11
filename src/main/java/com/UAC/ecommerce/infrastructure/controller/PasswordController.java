package com.UAC.ecommerce.infrastructure.controller;

import com.UAC.ecommerce.application.service.EmailService;
import com.UAC.ecommerce.application.service.UserService;
import com.UAC.ecommerce.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;

@Controller
@Slf4j
public class PasswordController {

    private UserService userService;
    private EmailService emailService;
    private final PasswordEncoder passwordEncoder;


    public PasswordController(UserService userService, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/email")
    public String emailRestore(){
        return "forgot-password-page";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam("email") String email, Model model) {
        // Generar un enlace único para el usuario
        User user = userService.findByEmail(email);
        if (user != null){
            LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(3);
            String recoveryLink = generateRecoveryLink(email, expirationTime);

            // Enviar el correo electrónico al usuario
            String subject = "Recuperación de contraseña";
            String body = "Para restablecer tu contraseña, haz clic en el siguiente enlace: \n\n" + recoveryLink;
            emailService.sendEmail(email, subject, body);

            // Agregar un mensaje de éxito en el modelo para mostrar al usuario
            model.addAttribute("success", "Correo electrónico enviado con éxito.");
            return "send-email-success-page";

        }

        model.addAttribute("error", "Correo electrónico desconocido.");
        return "forgot-password-page";
    }

    private String generateRecoveryLink(String email, LocalDateTime expirationTime) {
        UUID uniqueId = UUID.randomUUID();
        String escapedEmail = org.thymeleaf.util.StringUtils.replace(email, "/", "%2F");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String expirationTimeStr = expirationTime.format(formatter);

        String encodedToken = Base64.getUrlEncoder().encodeToString(uniqueId.toString().getBytes(StandardCharsets.UTF_8));
        String encodedEmail = Base64.getUrlEncoder().encodeToString(email.getBytes(StandardCharsets.UTF_8));
        String encodedExpirationTime = Base64.getUrlEncoder().encodeToString(expirationTimeStr.getBytes(StandardCharsets.UTF_8));

        String resetLink = "http://localhost:9090/reset-password?token=" + encodedToken + "&email=" + encodedEmail
                + "&expires=" + encodedExpirationTime;

        return resetLink;
    }


    @RequestMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam("token") String encodedToken,
                                        @RequestParam("email") String encodedEmail,
                                        @RequestParam("expires") String encodedExpirationTime,
                                        Model model) {

        // Decodificar los parámetros
        String token = new String(Base64.getUrlDecoder().decode(encodedToken), StandardCharsets.UTF_8);
        String email = new String(Base64.getUrlDecoder().decode(encodedEmail), StandardCharsets.UTF_8);
        String expirationTimeStr = new String(Base64.getUrlDecoder().decode(encodedExpirationTime), StandardCharsets.UTF_8);
        LocalDateTime expirationTime = LocalDateTime.parse(expirationTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        LocalDateTime currentTime = LocalDateTime.now();

        if (currentTime.isAfter(expirationTime)) {
            // El enlace ha expirado, puedes mostrar un mensaje de error o redirigir a una página de error
            model.addAttribute("error", "El enlace se ha vencido, solicite cambio de contraseña nuevamente.");
            return "login";
        } else {
            // El enlace aún es válido, mostrar la página de restablecimiento de contraseña

            // Agregar el correo electrónico al modelo para utilizarlo en la vista
            model.addAttribute("email", email);
            model.addAttribute("token", token);
            log.info("email: {}", model.getAttribute("email"));
            log.info("token: {}", model.getAttribute("token"));
            log.info("expires: {}", model.getAttribute("expires"));
            log.info("currentTime", currentTime);
            return "reset-password-page";
        }
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {

        // Obtener el usuario correspondiente al correo electrónico
        User user = userService.findByEmail(email);

        if (user != null) {
            // Encriptar la nueva contraseña antes de guardarla
            user.setPassword(passwordEncoder.encode(password));

            // Guardar el usuario actualizado en la base de datos
            userService.saveUser(user);

            model.addAttribute("success", "¡Contraseña cambiada exitosamente!");
        } else {
            model.addAttribute("error", "No se pudo cambiar la contraseña. Por favor, intenta nuevamente.");
        }

        return "login";
    }

}
