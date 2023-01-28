package uz.mutalov.onlinemarket.service.base;

import uz.mutalov.onlinemarket.dto.base.BaseDTO;
import uz.mutalov.onlinemarket.dto.base.GenericDTO;
import uz.mutalov.onlinemarket.response.DataDTO;
import uz.mutalov.onlinemarket.response.ResponseEntity;

public interface GenericCrudService<
        D extends GenericDTO,
        CD extends BaseDTO,
        UD extends GenericDTO> extends BaseService {

    ResponseEntity<DataDTO<D>> create(CD dto);

    ResponseEntity<DataDTO<D>> update(UD dto);

    ResponseEntity<DataDTO<Long>> delete(Long dto);
}
