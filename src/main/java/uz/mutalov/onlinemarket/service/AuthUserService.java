package uz.mutalov.onlinemarket.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.mutalov.onlinemarket.criteria.base.AbstractCriteria;
import uz.mutalov.onlinemarket.dto.auth.AuthUserCreateDTO;
import uz.mutalov.onlinemarket.dto.auth.AuthUserDTO;
import uz.mutalov.onlinemarket.dto.auth.AuthUserUpdateDTO;
import uz.mutalov.onlinemarket.dto.cart.CartCreateDTO;
import uz.mutalov.onlinemarket.entity.AuthUser;
import uz.mutalov.onlinemarket.entity.Cart;
import uz.mutalov.onlinemarket.entity.Product;
import uz.mutalov.onlinemarket.exceptions.NotFoundException;
import uz.mutalov.onlinemarket.mappers.AuthUserMapper;
import uz.mutalov.onlinemarket.repository.AuthUserRepository;
import uz.mutalov.onlinemarket.repository.CartRepository;
import uz.mutalov.onlinemarket.repository.ProductRepository;
import uz.mutalov.onlinemarket.response.DataDTO;
import uz.mutalov.onlinemarket.response.ResponseEntity;
import uz.mutalov.onlinemarket.service.base.AbstractService;
import uz.mutalov.onlinemarket.service.base.GenericCrudService;
import uz.mutalov.onlinemarket.service.base.GenericService;

import java.util.List;

@Service
public class AuthUserService extends AbstractService<AuthUserRepository, AuthUserMapper>
        implements GenericCrudService<AuthUserDTO, AuthUserCreateDTO, AuthUserUpdateDTO>, GenericService<AuthUserDTO, AbstractCriteria> {

    private final PasswordEncoder passwordEncoder;

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public AuthUserService(AuthUserRepository repository, @Qualifier("authUserMapperImpl") AuthUserMapper mapper, PasswordEncoder passwordEncoder, ProductRepository productRepository, CartRepository cartRepository) {
        super(repository, mapper);
        this.passwordEncoder = passwordEncoder;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public ResponseEntity<DataDTO<AuthUserDTO>> create(AuthUserCreateDTO dto) {
        AuthUser authUser = mapper.fromCreateDTO(dto);
        authUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        AuthUser save = repository.save(authUser);
        AuthUserDTO authUserDTO = mapper.toDTO(save);
        return new ResponseEntity<>(new DataDTO<>(authUserDTO));
    }

    @Override
    public ResponseEntity<DataDTO<AuthUserDTO>> update(AuthUserUpdateDTO dto) {
        AuthUser user = getAuthUserById(dto.getId());
        AuthUser authUser = mapper.fromUpdateDTO(dto, user);
        AuthUser save = repository.save(authUser);
        AuthUserDTO authUserDTO = mapper.toDTO(save);
        return new ResponseEntity<>(new DataDTO<>(authUserDTO));
    }

    @Override
    public ResponseEntity<DataDTO<Long>> delete(Long id) {
        AuthUser user = getAuthUserById(id);
        user.setDeleted(true);
        AuthUser save = repository.save(user);
        return new ResponseEntity<>(new DataDTO<>(save.getId()));
    }

    @Override
    public ResponseEntity<DataDTO<AuthUserDTO>> get(Long id) {
        AuthUser user = getAuthUserById(id);
        AuthUserDTO authUserDTO = mapper.toDTO(user);
        return new ResponseEntity<>(new DataDTO<>(authUserDTO));
    }

    @Override
    public ResponseEntity<DataDTO<List<AuthUserDTO>>> getAll(AbstractCriteria criteria) {
        PageRequest of = PageRequest.of(criteria.getPage(), criteria.getSize());
        List<AuthUser> content = repository.findAll(of).getContent();
        List<AuthUserDTO> authUserDTOS = mapper.toDTO(content);
        return new ResponseEntity<>(new DataDTO<>(authUserDTOS, authUserDTOS.size()));
    }

    public AuthUser getAuthUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public ResponseEntity<DataDTO<AuthUserDTO>> saveProductToCart(CartCreateDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found"));
        AuthUser user = getAuthUserByEmail();
        Cart cart = new Cart(dto.getAmount(), product);
        List<Cart> carts = user.getCarts();
        carts.add(cart);
        user.setCarts(carts);
        AuthUser save = repository.save(user);
        AuthUserDTO authUserDTO = mapper.toDTO(save);
        return new ResponseEntity<>(new DataDTO<>(authUserDTO));
    }

    public ResponseEntity<DataDTO<Integer>> removeProductFromCart(Integer id) {
        AuthUser user = getAuthUserByEmail();
        List<Cart> carts = user.getCarts();
        carts.removeIf(cart -> cart.getId().equals(id));
        user.setCarts(carts);
        repository.save(user);
        cartRepository.deleteById(id);
        return new ResponseEntity<>(new DataDTO<>(id));
    }

    public AuthUser getAuthUserByEmail() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return repository
                .findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

    }
}
