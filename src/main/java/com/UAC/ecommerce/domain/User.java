package com.UAC.ecommerce.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String personalId;
    private String email;
    private String address;
    private String cellphone;
    private String password;
    private UserType userType;
    private String userStatus;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime dateCreated;
}
