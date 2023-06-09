package com.UAC.ecommerce.infrastructure.entity;

import com.UAC.ecommerce.domain.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="products")
@Data
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String code;
    private String name;
    private String description;
    private String image;
    private BigDecimal price;

    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;


    @ManyToOne
    @JoinColumn(name = "user_entity_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "category_entity_id")
    private  CategoryEntity categoryEntity;

    public CategoryEntity getCategoryEntity(){
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity){
        this.categoryEntity = categoryEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
