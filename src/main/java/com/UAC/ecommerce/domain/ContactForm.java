package com.UAC.ecommerce.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactForm {


    private String destinatario;
    private String telefono;
    private String mensaje;
    private String name;
}
