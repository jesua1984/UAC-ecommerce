package com.UAC.ecommerce.domain;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
@Data
@ToString
public class Stock {
    private Long id;
    private LocalDateTime dateCreated;
    private Integer unitIn;
    private Integer unitOut;
    private String description;
    private Integer balance;
    private Product product;
}
