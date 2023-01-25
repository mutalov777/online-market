package uz.mutalov.onlinemarket.mappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import uz.mutalov.onlinemarket.dto.cart.CartCreateDTO;
import uz.mutalov.onlinemarket.dto.cart.CartDTO;
import uz.mutalov.onlinemarket.entity.Cart;
import uz.mutalov.onlinemarket.mappers.base.BaseMapper;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface CartMapper extends BaseMapper {

    Cart fromCreateDTO(CartCreateDTO dto);

    CartDTO toDTO(Cart dto);

    List<CartDTO> toDTO(List<Cart> dto);
}
