package uz.mutalov.onlinemarket.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;
import uz.mutalov.onlinemarket.dto.order.OrderCreateDTO;
import uz.mutalov.onlinemarket.dto.order.OrderDTO;
import uz.mutalov.onlinemarket.dto.order.OrderUpdateDTO;
import uz.mutalov.onlinemarket.entity.UserOrder;
import uz.mutalov.onlinemarket.mappers.base.GenericMapper;

import java.util.List;

@Component
@Mapper(componentModel = "spring",uses = {CartMapper.class},nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper extends GenericMapper<UserOrder, OrderDTO, OrderCreateDTO, OrderUpdateDTO> {
    @Override
    UserOrder fromCreateDTO(OrderCreateDTO dto);

    @Override
    UserOrder fromUpdateDTO(OrderUpdateDTO dto, @MappingTarget UserOrder entity);

    @Override
    OrderDTO toDTO(UserOrder entity);

    @Override
    List<OrderDTO> toDTO(List<UserOrder> entity);
}
