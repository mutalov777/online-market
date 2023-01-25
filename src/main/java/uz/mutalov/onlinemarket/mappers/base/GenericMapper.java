package uz.mutalov.onlinemarket.mappers.base;

import uz.mutalov.onlinemarket.dto.base.BaseDTO;
import uz.mutalov.onlinemarket.dto.base.GenericDTO;
import uz.mutalov.onlinemarket.entity.base.BaseEntity;

import java.util.List;

public interface GenericMapper<E extends BaseEntity, D extends GenericDTO, CD extends BaseDTO, UD extends GenericDTO> extends BaseMapper {

    E fromCreateDTO(CD dto);

    E fromUpdateDTO(UD dto, E entity);

    D toDTO(E entity);

    List<D> toDTO(List<E> entity);
}
