package com.UAC.ecommerce.infrastructure.mapper;

import com.UAC.ecommerce.domain.Product;
import com.UAC.ecommerce.infrastructure.entity.ProductEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring",uses={UserMapper.class})

public interface ProductMapper {
    @Mappings(
            {
                  @Mapping(source="id", target = "id"),
                    @Mapping(source="code", target = "code"),
                    @Mapping(source="name", target = "name"),
                    @Mapping(source="description", target = "description"),
                    @Mapping(source="image", target = "image"),
                    @Mapping(source="price", target = "price"),
                    @Mapping(source="dateCreated", target = "dateCreated"),
                    @Mapping(source="dateUpdated", target = "dateUpdated"),
                    @Mapping(source="userEntity", target = "user")

            }
    )
    Product toProduct(ProductEntity productEntity);
    Iterable<Product> toProduct(Iterable<ProductEntity> productEntities);

    @InheritInverseConfiguration
    ProductEntity toProductEntity(Product product);

}
