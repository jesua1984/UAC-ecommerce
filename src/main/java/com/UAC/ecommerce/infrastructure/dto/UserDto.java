package com.UAC.ecommerce.infrastructure.dto;

import com.UAC.ecommerce.domain.User;
import com.UAC.ecommerce.domain.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {

    @NotBlank(message = "Nombre es requerido")
    private String firstName;
    @NotBlank(message = "Apellido es requerido")
    private String lastName;
    @Email(message = "Debe ingresar un email valido")
    private String email;
    @NotBlank(message = "Dirección es requerida")
    private String address;
    @NotBlank(message = "Celular es requerido")
    @Size(min = 10, max = 10, message = "El numéro de celular debe ser de 10 dígitos")
    private String cellphone;
    @NotBlank(message = "Contraseña es requerida")
    private String password;

    public User userDtoToUser(){
        return new User(null,this.getEmail(),this.getFirstName(), this.getLastName(), this.getEmail(),this.getAddress(),
                this.getCellphone(),this.getPassword(), UserType.USER, LocalDateTime.now());
    }
}
