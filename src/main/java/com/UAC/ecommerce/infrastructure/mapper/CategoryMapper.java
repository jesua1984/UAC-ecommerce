package com.UAC.ecommerce.infrastructure.mapper;

import com.UAC.ecommerce.domain.Category;
import com.UAC.ecommerce.infrastructure.entity.CategoryEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mappings(
            {
                    @Mapping(source = "id", target = "id") ,
                    @Mapping(source="name", target="name"),
                    @Mapping(source="description", target="description")

            }
    )
    Category toCategory(CategoryEntity categoryEntity);

    Iterable<Category>toCategories(Iterable<CategoryEntity> categoryEntities);
    @InheritInverseConfiguration
    CategoryEntity toCategoryEntity(Category category);

    default Page<Category> toCategoryPage(Page<CategoryEntity> categoryEntityPage){
        return categoryEntityPage.map(this::toCategory);
    }
}
