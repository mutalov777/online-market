package uz.mutalov.onlinemarket.service.base;

import uz.mutalov.onlinemarket.criteria.base.BaseCriteria;
import uz.mutalov.onlinemarket.dto.base.GenericDTO;
import uz.mutalov.onlinemarket.response.DataDTO;
import uz.mutalov.onlinemarket.response.ResponseEntity;

import java.util.List;

public interface GenericService<D extends GenericDTO,C extends BaseCriteria> extends BaseService {

    ResponseEntity<DataDTO<D>> get(Long id);

    ResponseEntity<DataDTO<List<D>>> getAll(C criteria);
}
