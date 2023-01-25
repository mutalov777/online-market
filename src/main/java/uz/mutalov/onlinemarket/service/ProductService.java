package uz.mutalov.onlinemarket.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.mutalov.onlinemarket.criteria.ProductCriteria;
import uz.mutalov.onlinemarket.dto.product.ProductCreateDTO;
import uz.mutalov.onlinemarket.dto.product.ProductDTO;
import uz.mutalov.onlinemarket.dto.product.ProductUpdateDTO;
import uz.mutalov.onlinemarket.entity.Product;
import uz.mutalov.onlinemarket.entity.ProductCategory;
import uz.mutalov.onlinemarket.exceptions.NotFoundException;
import uz.mutalov.onlinemarket.mappers.ProductMapper;
import uz.mutalov.onlinemarket.repository.CategoryRepository;
import uz.mutalov.onlinemarket.repository.ProductRepository;
import uz.mutalov.onlinemarket.response.DataDTO;
import uz.mutalov.onlinemarket.response.ResponseEntity;
import uz.mutalov.onlinemarket.service.base.AbstractService;
import uz.mutalov.onlinemarket.service.base.GenericCrudService;
import uz.mutalov.onlinemarket.service.base.GenericService;

import java.util.List;
import java.util.Objects;

@Service
public class ProductService extends AbstractService<ProductRepository, ProductMapper>
        implements GenericService<ProductDTO, ProductCriteria>,
        GenericCrudService<ProductCreateDTO, ProductUpdateDTO> {
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository repository,
                          @Qualifier("productMapperImpl") ProductMapper mapper,
                          CategoryRepository categoryRepository) {
        super(repository, mapper);
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<DataDTO<Long>> create(ProductCreateDTO dto) {
        ProductCategory category = getCategoryByName(dto.getCategory());
        Product product = mapper.fromCreateDTO(dto);
        product.setCategory(category);
        Product save = repository.save(product);
        return new ResponseEntity<>(new DataDTO<>(save.getId()));
    }

    @Override
    public ResponseEntity<DataDTO<Long>> update(ProductUpdateDTO dto) {
        Product product = getProductById(dto.getId());
        Product updatedProduct = mapper.fromUpdateDTO(dto, product);
        ProductCategory category;
        if (Objects.nonNull(dto.getCategory())) {
            category = getCategoryByName(dto.getCategory());
            updatedProduct.setCategory(category);
        }
        Product save = repository.save(updatedProduct);
        return new ResponseEntity<>(new DataDTO<>(save.getId()));
    }

    @Override
    public ResponseEntity<DataDTO<Long>> delete(Long dto) {
        Product product = getProductById(dto);
        repository.delete(product);
        return new ResponseEntity<>(new DataDTO<>(dto));
    }

    @Override
    public ResponseEntity<DataDTO<ProductDTO>> get(Long id) {
        Product productById = getProductById(id);
        ProductDTO productDTO = mapper.toDTO(productById);
        return new ResponseEntity<>(new DataDTO<>(productDTO));
    }

    @Override
    public ResponseEntity<DataDTO<List<ProductDTO>>> getAll(ProductCriteria criteria) {
        List<Product> products;
        Pageable of = PageRequest.of(criteria.getPage(), criteria.getSize());
        if (Objects.nonNull(criteria.getCategory())) {
            products = repository.findAllByCategoryName(criteria.getCategory(),of)
                    .orElseThrow(()->new NotFoundException("Product not found by this criteria"));
        } else {
            products = repository.findAll(of).getContent();
        }
        List<ProductDTO> productDTOS = mapper.toDTO(products);
        return new ResponseEntity<>(new DataDTO<>(productDTOS));
    }


    private Product getProductById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }


    private ProductCategory getCategoryByName(String dto) {
        return categoryRepository
                .findByName(dto)
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }
}
