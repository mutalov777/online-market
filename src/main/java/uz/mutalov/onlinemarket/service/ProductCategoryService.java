package uz.mutalov.onlinemarket.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.mutalov.onlinemarket.service.base.AbstractService;
import uz.mutalov.onlinemarket.dto.category.ProductCategoryCreateDTO;
import uz.mutalov.onlinemarket.dto.category.ProductCategoryDTO;
import uz.mutalov.onlinemarket.dto.category.ProductCategoryUpdateDTO;
import uz.mutalov.onlinemarket.entity.ProductCategory;
import uz.mutalov.onlinemarket.exceptions.NotFoundException;
import uz.mutalov.onlinemarket.mappers.CategoryMapper;
import uz.mutalov.onlinemarket.repository.CategoryRepository;
import uz.mutalov.onlinemarket.response.DataDTO;
import uz.mutalov.onlinemarket.response.ResponseEntity;

import java.util.List;
import java.util.Objects;

@Service
public class ProductCategoryService extends AbstractService<CategoryRepository, CategoryMapper> {


    public ProductCategoryService(CategoryRepository repository, @Qualifier("categoryMapperImpl") CategoryMapper mapper) {
        super(repository, mapper);
    }


    public ResponseEntity<DataDTO<Integer>> create(ProductCategoryCreateDTO dto) {
        ProductCategory productCategory = mapper.fromCreateDTO(dto);
        ProductCategory productCategory1 = setCategoryGroup(dto.getGroups(), productCategory);
        ProductCategory save = repository.save(productCategory1);
        return new ResponseEntity<>(new DataDTO<>(save.getId()));
    }


    public ResponseEntity<DataDTO<Integer>> update(ProductCategoryUpdateDTO dto) {
        ProductCategory category = getCategoryById(dto.getId());
        ProductCategory productCategory = mapper.fromUpdateDTO(dto, category);
        ProductCategory productCategory1 = setCategoryGroup(dto.getGroups(), productCategory);
        ProductCategory save = repository.save(productCategory1);
        return new ResponseEntity<>(new DataDTO<>(save.getId()));
    }


    public ResponseEntity<DataDTO<Integer>> delete(Integer id) {
        ProductCategory categoryById = getCategoryById(id);
        repository.delete(categoryById);
        return new ResponseEntity<>(new DataDTO<>(id));
    }

    public ResponseEntity<DataDTO<List<ProductCategoryDTO>>> getAll() {
        List<ProductCategory> all = repository.findAll();
        List<ProductCategoryDTO> productCategoryDTOS = mapper.toDTO(all);
        return new ResponseEntity<>(new DataDTO<>(productCategoryDTOS, productCategoryDTOS.size()));
    }

    private ProductCategory getCategoryById(Integer id) {
        return repository
                .findById(id).orElseThrow(() -> new NotFoundException("Category Not Found"));
    }

    private ProductCategory setCategoryGroup(List<String> groups, ProductCategory category) {
        if (Objects.isNull(groups)) {
            return category;
        }
        groups
                .stream()
                .map(item -> repository.findByName(item).orElseThrow(() -> new NotFoundException("Category Not Found")))
                .forEach(f -> category.getGroups().add(f));
        return category;
    }
}
