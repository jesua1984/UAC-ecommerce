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

    private String username;
    @NotBlank(message = "Nombre es requerido")
    private String firstName;
    @NotBlank(message = "Apellido es requerido")
    private String lastName;

    @NotBlank(message = "cédula de es requerida")
    private String personalId;
    @Email(message = "Debe ingresar un email valido")
    private String email;
    @NotBlank(message = "Dirección es requerida")
    private String address;
    @NotBlank(message = "Celular es requerido")
    @Size(min = 10, max = 10, message = "El numéro de celular debe ser de 10 dígitos")
    private String cellphone;
    @NotBlank(message = "Contraseña es requerida")
    private String password;
    @NotBlank(message = "Contraseña es requerida")
    private String confirmPassword;
    private String userStatus;


    public User userDtoToUser(){
        return new User(null,this.getEmail(),this.getFirstName(), this.getLastName(), this.getPersonalId(), this.getEmail(),this.getAddress(),
                this.getCellphone(),this.getPassword(), UserType.USER, this.getUserStatus(), LocalDateTime.now());
    }
}
