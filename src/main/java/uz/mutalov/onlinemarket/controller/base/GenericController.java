package uz.mutalov.onlinemarket.controller.base;

import uz.mutalov.onlinemarket.criteria.base.BaseCriteria;
import uz.mutalov.onlinemarket.dto.base.GenericDTO;
import uz.mutalov.onlinemarket.response.DataDTO;
import uz.mutalov.onlinemarket.response.ResponseEntity;

import java.util.List;
/**
 *
 * @param <D> dto
 * @param <C> criteria
 * */
public interface GenericController<D extends GenericDTO,C extends BaseCriteria> extends BaseController{

    ResponseEntity<DataDTO<D>> get(Long id);

    ResponseEntity<DataDTO<List<D>>> getAll(C criteria);
}

