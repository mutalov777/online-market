package uz.mutalov.onlinemarket.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.mutalov.onlinemarket.dto.category.ProductCategoryCreateDTO;
import uz.mutalov.onlinemarket.dto.category.ProductCategoryDTO;
import uz.mutalov.onlinemarket.dto.category.ProductCategoryUpdateDTO;
import uz.mutalov.onlinemarket.entity.ProductCategory;
import uz.mutalov.onlinemarket.exceptions.NotFoundException;
import uz.mutalov.onlinemarket.mappers.CategoryMapper;
import uz.mutalov.onlinemarket.repository.CategoryRepository;
import uz.mutalov.onlinemarket.response.DataDTO;
import uz.mutalov.onlinemarket.response.ResponseEntity;
import uz.mutalov.onlinemarket.service.base.AbstractService;

import java.util.List;

@Service
public class ProductCategoryService extends AbstractService<CategoryRepository, CategoryMapper> {


    public ProductCategoryService(CategoryRepository repository, @Qualifier("categoryMapperImpl") CategoryMapper mapper) {
        super(repository, mapper);
    }


    public ResponseEntity<DataDTO<ProductCategoryDTO>> create(ProductCategoryCreateDTO dto) {
        ProductCategory productCategory = mapper.fromCreateDTO(dto);
        ProductCategory save = repository.save(productCategory);
        ProductCategoryDTO productCategoryDTO = mapper.toDTO(save);
        return new ResponseEntity<>(new DataDTO<>(productCategoryDTO));
    }


    public ResponseEntity<DataDTO<ProductCategoryDTO>> update(ProductCategoryUpdateDTO dto) {
        ProductCategory category = getCategoryById(dto.getId());
        ProductCategory productCategory = mapper.fromUpdateDTO(dto, category);
        ProductCategory save = repository.save(productCategory);
        ProductCategoryDTO productCategoryDTO = mapper.toDTO(save);
        return new ResponseEntity<>(new DataDTO<>(productCategoryDTO));
    }


    public ResponseEntity<DataDTO<Integer>> delete(Integer id) {
        repository.deleteById(id);
        return new ResponseEntity<>(new DataDTO<>(id));
    }

    public ResponseEntity<DataDTO<List<ProductCategoryDTO>>> getAll() {
        List<ProductCategory> all = repository.findAll();
        List<ProductCategoryDTO> productCategoryDTOS = mapper.toDTO(all);
        long count = repository.count();
        return new ResponseEntity<>(new DataDTO<>(productCategoryDTOS, count));
    }

    private ProductCategory getCategoryById(Integer id) {
        return repository
                .findById(id).orElseThrow(() -> new NotFoundException("Category Not Found"));
    }

    public ResponseEntity<DataDTO<List<String>>> getCategoryByName(String name) {
        List<String> names = repository.findNameStartWith(name.toUpperCase() + "%");
        return new ResponseEntity<>(new DataDTO<>(names));
    }
}
