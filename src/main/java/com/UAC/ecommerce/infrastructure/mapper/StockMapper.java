package com.UAC.ecommerce.infrastructure.mapper;

import com.UAC.ecommerce.domain.Stock;
import com.UAC.ecommerce.infrastructure.entity.StockEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface StockMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "dateCreated", target = "dateCreated"),
            @Mapping(source = "unitIn", target = "unitIn"),
            @Mapping(source = "unitOut", target = "unitOut"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "balance", target = "balance"),
            @Mapping(source = "productEntity", target = "product")
    }
    )
    Stock toStock(StockEntity stockEntity);
    List<Stock> toStocks(List<StockEntity> productEntities);
    @InheritInverseConfiguration
    StockEntity toStockEntity(Stock stock);
}
