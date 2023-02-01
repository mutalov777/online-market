package uz.mutalov.onlinemarket.controller;

import org.springframework.web.bind.annotation.*;
import uz.mutalov.onlinemarket.controller.base.AbstractController;
import uz.mutalov.onlinemarket.dto.category.ProductCategoryCreateDTO;
import uz.mutalov.onlinemarket.dto.category.ProductCategoryDTO;
import uz.mutalov.onlinemarket.dto.category.ProductCategoryUpdateDTO;
import uz.mutalov.onlinemarket.response.DataDTO;
import uz.mutalov.onlinemarket.response.ResponseEntity;
import uz.mutalov.onlinemarket.service.ProductCategoryService;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController extends AbstractController<ProductCategoryService> {

    public CategoryController(ProductCategoryService service) {
        super(service);
    }

    @PostMapping("/create")
    public ResponseEntity<DataDTO<ProductCategoryDTO>> create(@RequestBody ProductCategoryCreateDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/update")
    public ResponseEntity<DataDTO<ProductCategoryDTO>> update(@RequestBody ProductCategoryUpdateDTO dto) {
        return service.update(dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DataDTO<Integer>> delete(@PathVariable Integer id) {
        return service.delete(id);
    }

    @GetMapping("/get-list")
    public ResponseEntity<DataDTO<List<ProductCategoryDTO>>> getALl() {
        return service.getAll();
    }

    @GetMapping("/get-category-name/{name}")
    public ResponseEntity<DataDTO<List<String>>> getCategoryByName(@PathVariable String name) {
        return service.getCategoryByName(name);
    }
}
