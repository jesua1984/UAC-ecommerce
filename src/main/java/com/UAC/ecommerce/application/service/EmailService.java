package com.UAC.ecommerce.application.service;

import com.UAC.ecommerce.domain.ContactForm;
import com.UAC.ecommerce.domain.Order;
import com.UAC.ecommerce.domain.OrderProduct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


public class EmailService {
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

 /*   public void enviarCorreoOrdenCreada(String destinatario, Order order, List<OrderProduct> orderProducts) {
        try {
            MimeMessage mensaje = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(destinatario);
            helper.setFrom("contactopescaderiafidyfer@gmail.com");
            helper.setSubject("Orden Creada - Detalles de la Orden");

            // Construir el contenido del correo con los detalles de la orden y sus productos
            StringBuilder contenido = new StringBuilder();
            contenido.append("Estimado/a ").append(order.getUser()).append(",\n\n");
            contenido.append("Se ha creado exitosamente la siguiente orden:\n\n");
            contenido.append("Número de Orden: ").append(order.getId()).append("\n");
            contenido.append("Fecha de Creación: ").append(order.getDateCreated()).append("\n\n");
            contenido.append("Detalles de la Orden:\n\n");

            orderProducts = order.getOrderProducts();
            for (OrderProduct orderProduct : orderProducts) {
                contenido.append("Producto: ").append(orderProduct.getProduct().getName()).append("\n");
                contenido.append("Cantidad: ").append(orderProduct.getQuantity()).append("\n");
                contenido.append("Precio Unitario: ").append(orderProduct.getProduct().getPrice()).append("\n\n");
            }

            contenido.append("Precio Total: ").append(order.getTotalOrderPrice()).append("\n\n");
            contenido.append("Gracias por tu compra.\n");
            contenido.append("Equipo de la Pescadería Fidyfer");

            helper.setText(contenido.toString());

            javaMailSender.send(mensaje);
        } catch (MessagingException | MailException e) {
            // Manejo de excepciones en caso de que falle el envío del correo electrónico
        }
    }*/


    public void enviarFormularioDeContacto(ContactForm contactForm) {
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();

            mensaje.setTo("contactopescaderiafidyfer@gmail.com");
            mensaje.setFrom("contactopescaderiafidyfer@gmail.com");
            mensaje.setSubject("Correo de Contacto");
            String contenido = "Nombre: " + contactForm.getName() + "\n";
            contenido += "Email: " + contactForm.getDestinatario() + "\n";
            contenido += "Teléfono: " + contactForm.getTelefono() + "\n";
            contenido += "Mensaje: " + contactForm.getMensaje();

            mensaje.setText(contenido);
            javaMailSender.send(mensaje);
        } catch (MailException e) {
            // Manejo de excepciones en caso de que falle el envío del correo electrónico
        }
    }

    // ...

    public void enviarCorreoBienvenida(String email) {
        try {
            MimeMessage mensaje = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(email);
            helper.setFrom("contactopescaderiafidyfer@gmail.com");
            helper.setSubject("Se ha creado tu cuenta en la Pescadería Fidyfer");

            String contenido = cargarContenidoHTML("/bienvenida.html");
            helper.setText(contenido, true);

            javaMailSender.send(mensaje);
        } catch (MessagingException | MailException | IOException e) {
            // Manejo de excepciones en caso de que falle el envío del correo electrónico
        }
    }

    private String cargarContenidoHTML(String nombreArchivo) throws IOException {
        ClassPathResource resource = new ClassPathResource("templates" + nombreArchivo);
        byte[] fileBytes = Files.readAllBytes(resource.getFile().toPath());
        return new String(fileBytes);
    }

// ...


}
