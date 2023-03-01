package uz.mutalov.onlinemarket.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;
import uz.mutalov.onlinemarket.dto.category.ProductCategoryCreateDTO;
import uz.mutalov.onlinemarket.dto.category.ProductCategoryDTO;
import uz.mutalov.onlinemarket.dto.category.ProductCategoryUpdateDTO;
import uz.mutalov.onlinemarket.entity.ProductCategory;
import uz.mutalov.onlinemarket.mappers.base.BaseMapper;

import java.util.List;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper extends BaseMapper {
    ProductCategory fromCreateDTO(ProductCategoryCreateDTO dto);

    ProductCategory fromUpdateDTO(ProductCategoryUpdateDTO dto, @MappingTarget ProductCategory entity);

    ProductCategoryDTO toDTO(ProductCategory entity);

    List<ProductCategoryDTO> toDTO(List<ProductCategory> entity);
}
