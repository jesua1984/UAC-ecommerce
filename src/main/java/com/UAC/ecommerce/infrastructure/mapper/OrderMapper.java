package com.UAC.ecommerce.infrastructure.mapper;

import com.UAC.ecommerce.domain.Order;
import com.UAC.ecommerce.infrastructure.entity.OrderEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface OrderMapper {
    @Mappings(
            {
                    @Mapping(source="id", target="id"),
                    @Mapping(source="dateCreated", target="dateCreated"),
                    @Mapping(source="orderStatus", target="orderStatus"),
                    @Mapping(source="user", target="user")

            }
    )
    Order toOrder(OrderEntity orderEntity);
    Iterable<Order>toOrders(Iterable<OrderEntity> orderEntities);
    @InheritInverseConfiguration
    OrderEntity toOrderEntity(Order order);

}
