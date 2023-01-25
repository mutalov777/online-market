package uz.mutalov.onlinemarket.controller.base;

import uz.mutalov.onlinemarket.criteria.base.BaseCriteria;
import uz.mutalov.onlinemarket.dto.base.BaseDTO;
import uz.mutalov.onlinemarket.dto.base.GenericDTO;
import uz.mutalov.onlinemarket.response.DataDTO;
import uz.mutalov.onlinemarket.response.ResponseEntity;

public interface GenericCrudController<
        CD extends BaseDTO,
        UD extends GenericDTO> extends BaseController {

    ResponseEntity<DataDTO<Long>> create(CD dto);

    ResponseEntity<DataDTO<Long>> update(UD dto);

    ResponseEntity<DataDTO<Long>> delete(Long id);
}

