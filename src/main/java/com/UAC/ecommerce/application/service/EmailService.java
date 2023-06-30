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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class EmailService {
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void enviarCorreoOrdenCreada(String destinatario, Order order, List<OrderProduct> orderProducts) {
        try {

            MimeMessage mensaje = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");
            LocalDateTime dateCreated = order.getDateCreated();
            String formattedDate = "";
            if (dateCreated != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                formattedDate = dateCreated.format(formatter);
            }

            helper.setTo(destinatario);
            helper.setFrom("rokero_nato@hotmail.com");
            helper.setSubject("Su orden ha sido generada");

            // Construir el contenido del correo con los detalles de la orden y sus productos

            StringBuilder contenido = new StringBuilder();
            contenido.append("<html><body>");
            contenido.append("<h2>Estimado/a ").append(order.getUser().getFirstName()).append(" ");
            contenido.append(order.getUser().getLastName()).append("</h2><br/>");
            contenido.append("<p>Se ha creado exitosamente la siguiente orden de compra:</p><br/>");
            contenido.append("<p><strong>Número de Orden:</strong> ").append(order.getId()).append("</p>");
            contenido.append("<p><strong>Fecha de Creación:</strong> ").append(formattedDate).append("</p><br/>");
            contenido.append("<p><strong>Detalles de la Orden:</strong></p><br/>");
            contenido.append("<table border='1'>");
            contenido.append("<tr><th>Producto</th><th>Precio</th><th>Cantidad</th></tr>");

            orderProducts.forEach(
                    op -> {
                        contenido.append("<tr>");
                        contenido.append("<td>").append(op.getProduct().getName()).append("</td>");
                        contenido.append("<td>").append(op.getProduct().getPrice()).append("</td>");
                        contenido.append("<td>").append(op.getQuantity()).append("</td>");
                        contenido.append("</tr>");
                    }
            );

            contenido.append("</table><br/>");
            //contenido.append("Precio Total: ").append(order.getTotalOrderPrice()).append("\n\n");
            contenido.append("<p>Gracias por tu compra.</p>");
            contenido.append("<p>Equipo de la Pescadería Fidyfer</p>");
            contenido.append("</body></html>");

            helper.setText(contenido.toString(), true);

            javaMailSender.send(mensaje);
        } catch (MessagingException | MailException e) {
            // Manejo de excepciones en caso de que falle el envío del correo electrónico
        }
    }


    public void enviarFormularioDeContacto(ContactForm contactForm) {
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();

            mensaje.setTo("rokero_nato@hotmail.com");
            mensaje.setFrom("rokero_nato@hotmail.com");
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
