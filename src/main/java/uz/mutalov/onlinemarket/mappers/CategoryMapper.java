package uz.mutalov.onlinemarket.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import uz.mutalov.onlinemarket.dto.category.ProductCategoryCreateDTO;
import uz.mutalov.onlinemarket.dto.category.ProductCategoryDTO;
import uz.mutalov.onlinemarket.dto.category.ProductCategoryUpdateDTO;
import uz.mutalov.onlinemarket.entity.ProductCategory;
import uz.mutalov.onlinemarket.mappers.base.BaseMapper;

import java.util.List;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper extends BaseMapper {
    @Mapping(target = "groups", ignore = true)
    ProductCategory fromCreateDTO(ProductCategoryCreateDTO dto);

    @Mapping(target = "groups", ignore = true)
    ProductCategory fromUpdateDTO(ProductCategoryUpdateDTO dto, @MappingTarget ProductCategory entity);

    @Mapping(target = "groups", expression = "java(entity.getGroups().stream().map(ProductCategory::getName).toList())")
    ProductCategoryDTO toDTO(ProductCategory entity);

    @Mapping(target = "groups", expression = "java(entity.getGroups().stream().map(ProductCategory::getName).toList())")
    List<ProductCategoryDTO> toDTO(List<ProductCategory> entity);
}
