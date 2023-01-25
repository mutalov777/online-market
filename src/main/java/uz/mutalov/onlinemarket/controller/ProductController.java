package uz.mutalov.onlinemarket.controller;

import org.springframework.web.bind.annotation.*;
import uz.mutalov.onlinemarket.controller.base.AbstractController;
import uz.mutalov.onlinemarket.controller.base.GenericController;
import uz.mutalov.onlinemarket.controller.base.GenericCrudController;
import uz.mutalov.onlinemarket.criteria.ProductCriteria;
import uz.mutalov.onlinemarket.criteria.base.AbstractCriteria;
import uz.mutalov.onlinemarket.dto.product.ProductCreateDTO;
import uz.mutalov.onlinemarket.dto.product.ProductDTO;
import uz.mutalov.onlinemarket.dto.product.ProductUpdateDTO;
import uz.mutalov.onlinemarket.response.DataDTO;
import uz.mutalov.onlinemarket.response.ResponseEntity;
import uz.mutalov.onlinemarket.service.ProductService;

import java.util.List;
@RestController
@RequestMapping("/product")
public class ProductController extends AbstractController<ProductService>
        implements GenericController<ProductDTO, ProductCriteria>,
        GenericCrudController<ProductCreateDTO, ProductUpdateDTO> {

    public ProductController(ProductService service) {
        super(service);
    }

    @Override
    @GetMapping("/get/{id}")
    public ResponseEntity<DataDTO<ProductDTO>> get(@PathVariable Long id) {
        return service.get(id);
    }

    @Override
    @GetMapping("/get-list")
    public ResponseEntity<DataDTO<List<ProductDTO>>> getAll(ProductCriteria criteria) {
        return service.getAll(criteria);
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<DataDTO<Long>> create(@RequestBody ProductCreateDTO dto) {
        return service.create(dto);
    }

    @Override
    @PostMapping("/update")
    public ResponseEntity<DataDTO<Long>> update(@RequestBody ProductUpdateDTO dto) {
        return service.update(dto);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DataDTO<Long>> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
