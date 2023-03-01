package uz.mutalov.onlinemarket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.mutalov.onlinemarket.controller.base.AbstractController;
import uz.mutalov.onlinemarket.controller.base.GenericController;
import uz.mutalov.onlinemarket.controller.base.GenericCrudController;
import uz.mutalov.onlinemarket.criteria.ProductCriteria;
import uz.mutalov.onlinemarket.dto.product.ProductCreateDTO;
import uz.mutalov.onlinemarket.dto.product.ProductDTO;
import uz.mutalov.onlinemarket.dto.product.ProductUpdateDTO;
import uz.mutalov.onlinemarket.response.DataDTO;
import uz.mutalov.onlinemarket.response.ResponseEntity;
import uz.mutalov.onlinemarket.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController extends AbstractController<ProductService>
        implements GenericController<ProductDTO, ProductCriteria>,
        GenericCrudController<ProductDTO, ProductCreateDTO, ProductUpdateDTO> {

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
        log.info("Request for getAll Product");
        return service.getAll(criteria);
    }

    @Override
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DataDTO<ProductDTO>> create(@RequestBody ProductCreateDTO dto) {
        return service.create(dto);
    }

    @Override
    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DataDTO<ProductDTO>> update(@RequestBody ProductUpdateDTO dto) {
        return service.update(dto);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DataDTO<Long>> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
