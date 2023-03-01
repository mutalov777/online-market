package uz.mutalov.onlinemarket.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;
import uz.mutalov.onlinemarket.dto.cart.CartDTO;
import uz.mutalov.onlinemarket.entity.Cart;
import uz.mutalov.onlinemarket.mappers.base.BaseMapper;

import java.util.List;

@Component
@Mapper(componentModel = "spring", uses = {ProductMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CartMapper extends BaseMapper {
    CartDTO toDTO(Cart dto);

    List<CartDTO> toDTO(List<Cart> dto);
}
