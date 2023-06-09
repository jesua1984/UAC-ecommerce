package com.UAC.ecommerce.infrastructure.mapper;

import com.UAC.ecommerce.domain.User;
import com.UAC.ecommerce.infrastructure.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings(
            {
                    @Mapping(source = "id",target = "id"),
                    @Mapping(source = "username",target = "username"),
                    @Mapping(source = "firstName",target = "firstName"),
                    @Mapping(source = "lastName",target = "lastName"),
                    @Mapping(source = "personalId",target = "personalId"),
                    @Mapping(source = "email",target = "email"),
                    @Mapping(source = "address",target = "address"),
                    @Mapping(source = "cellphone",target = "cellphone"),
                    @Mapping(source = "password",target = "password"),
                    @Mapping(source = "userType",target = "userType"),
                    @Mapping(source = "userStatus",target = "userStatus"),
                    @Mapping(source = "dateCreated",target = "dateCreated")
            }
    )
    User toUser(UserEntity userEntity);

    Iterable<User> toUser(Iterable<UserEntity> userEntities);

    @InheritInverseConfiguration
    UserEntity toUserEntity(User user);

     default Page<User> toUserPage(Page<UserEntity> userEntityPage) {
        return userEntityPage.map(this::toUser);
    }

}
