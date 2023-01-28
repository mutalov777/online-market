package uz.mutalov.onlinemarket.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;
import uz.mutalov.onlinemarket.dto.message.MessageCreateDTO;
import uz.mutalov.onlinemarket.dto.message.MessageDTO;
import uz.mutalov.onlinemarket.dto.message.MessageUpdateDTO;
import uz.mutalov.onlinemarket.entity.Message;
import uz.mutalov.onlinemarket.mappers.base.GenericMapper;

import java.util.List;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MessageMapper extends GenericMapper<Message, MessageDTO, MessageCreateDTO, MessageUpdateDTO> {

    @Override
    @Mapping(target = "to", ignore = true)
    Message fromCreateDTO(MessageCreateDTO dto);

    @Override
    Message fromUpdateDTO(MessageUpdateDTO dto, @MappingTarget Message entity);

    @Override
    MessageDTO toDTO(Message entity);

    @Override
    List<MessageDTO> toDTO(List<Message> entity);
}
