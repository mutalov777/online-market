package uz.mutalov.onlinemarket.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;
import uz.mutalov.onlinemarket.dto.product.ProductCreateDTO;
import uz.mutalov.onlinemarket.dto.product.ProductDTO;
import uz.mutalov.onlinemarket.dto.product.ProductUpdateDTO;
import uz.mutalov.onlinemarket.entity.Product;
import uz.mutalov.onlinemarket.mappers.base.GenericMapper;

import java.util.List;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper extends GenericMapper<Product, ProductDTO, ProductCreateDTO, ProductUpdateDTO> {
    @Override
    @Mapping(target = "category", ignore = true)
    Product fromCreateDTO(ProductCreateDTO dto);

    @Override
    @Mapping(target = "category", ignore = true)
    Product fromUpdateDTO(ProductUpdateDTO dto, @MappingTarget Product entity);
    @Override
    ProductDTO toDTO(Product entity);
    @Override
    List<ProductDTO> toDTO(List<Product> entity);
}
