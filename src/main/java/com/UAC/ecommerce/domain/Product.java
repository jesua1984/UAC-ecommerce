package com.UAC.ecommerce.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@ToString
public class Product {
    private long id;
    private String code;
    private String name;
    private String description;
    private String image;
    private BigDecimal price;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime dateCreated;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime dateUpdated;

    private User user;

    public Product() {
        this.setCode(UUID.randomUUID().toString());
    }
}
