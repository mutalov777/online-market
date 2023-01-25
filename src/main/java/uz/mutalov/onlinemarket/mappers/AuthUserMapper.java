package uz.mutalov.onlinemarket.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;
import uz.mutalov.onlinemarket.dto.auth.AuthUserCreateDTO;
import uz.mutalov.onlinemarket.dto.auth.AuthUserDTO;
import uz.mutalov.onlinemarket.dto.auth.AuthUserUpdateDTO;
import uz.mutalov.onlinemarket.entity.AuthUser;
import uz.mutalov.onlinemarket.mappers.base.GenericMapper;

import java.util.List;

@Component
@Mapper(componentModel = "spring",uses = {ProductMapper.class,CartMapper.class},nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuthUserMapper extends GenericMapper<AuthUser, AuthUserDTO, AuthUserCreateDTO, AuthUserUpdateDTO> {

    @Override
    @Mapping(target = "password",ignore = true)
    AuthUser fromCreateDTO(AuthUserCreateDTO dto);

    @Override
    AuthUser fromUpdateDTO(AuthUserUpdateDTO dto, @MappingTarget AuthUser entity);

    @Override
    AuthUserDTO toDTO(AuthUser entity);

    @Override
    List<AuthUserDTO> toDTO(List<AuthUser> entity);
}
