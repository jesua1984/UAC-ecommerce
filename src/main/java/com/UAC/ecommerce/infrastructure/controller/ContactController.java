package com.UAC.ecommerce.infrastructure.controller;

import com.UAC.ecommerce.application.service.EmailService;
import com.UAC.ecommerce.domain.ContactForm;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/contact")
public class ContactController {
    private final EmailService emailService;

    public ContactController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping(value = "/enviar", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String enviarFormularioContacto(@RequestParam("name") String name,
                                           @RequestParam("destinatario") String destinatario,
                                           @RequestParam("telefono") String telefono,
                                           @RequestParam("mensaje") String mensaje, RedirectAttributes redirectAttributes) {
        ContactForm contactForm = new ContactForm();
        contactForm.setName(name);
        contactForm.setDestinatario(destinatario);
        contactForm.setTelefono(telefono);
        contactForm.setMensaje(mensaje);

        emailService.enviarFormularioDeContacto(contactForm);
        redirectAttributes.addFlashAttribute("mensaje", "Su mensaje se envió Correctamente")
                .addFlashAttribute("clase", "success");

        return "redirect:/home/contact";
    }

}
