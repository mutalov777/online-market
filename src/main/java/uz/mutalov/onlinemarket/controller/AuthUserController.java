package uz.mutalov.onlinemarket.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import uz.mutalov.onlinemarket.controller.base.AbstractController;
import uz.mutalov.onlinemarket.controller.base.GenericController;
import uz.mutalov.onlinemarket.controller.base.GenericCrudController;
import uz.mutalov.onlinemarket.criteria.AuthUserCriteria;
import uz.mutalov.onlinemarket.dto.auth.*;
import uz.mutalov.onlinemarket.dto.cart.CartCreateDTO;
import uz.mutalov.onlinemarket.response.DataDTO;
import uz.mutalov.onlinemarket.response.ResponseEntity;
import uz.mutalov.onlinemarket.service.AuthService;
import uz.mutalov.onlinemarket.service.AuthUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/auth/")
@Slf4j
public class AuthUserController extends AbstractController<AuthUserService> implements GenericCrudController<AuthUserDTO,
        AuthUserCreateDTO, AuthUserUpdateDTO>, GenericController<AuthUserDTO, AuthUserCriteria> {

    private final AuthService authService;

    public AuthUserController(AuthUserService service, AuthService authService) {
        super(service);
        this.authService = authService;
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<DataDTO<AuthUserDTO>> create(@RequestBody AuthUserCreateDTO dto) {
        log.info("Request for User Create");
        return service.create(dto);
    }


    @Override
    @PostMapping("/update")
    public ResponseEntity<DataDTO<AuthUserDTO>> update(@RequestBody AuthUserUpdateDTO dto) {
        log.info("Request to User Update");
        return service.update(dto);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DataDTO<Long>> delete(@PathVariable Long id) {
        log.info("Request to User Delete");
        return service.delete(id);
    }

    @Override
    @GetMapping("/get/{id}")
    public ResponseEntity<DataDTO<AuthUserDTO>> get(@PathVariable Long id) {
        log.info("Request to User get");
        return service.get(id);
    }

    @Override
    @GetMapping("get-list")
    public ResponseEntity<DataDTO<List<AuthUserDTO>>> getAll(AuthUserCriteria criteria) {
        log.info("Request to User getAll");
        return service.getAll(criteria);
    }

    @PostMapping(value = "/access-token")
    public ResponseEntity<DataDTO<SessionDTO>> getAccessToken(@RequestBody LoginDTO dto) {
        log.info("Request to getAccessToken ");
        return authService.getAccessToken(dto);
    }

    @SneakyThrows
    @GetMapping(value = "/refresh-token")
    public ResponseEntity<DataDTO<SessionDTO>> getRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        log.info("Request to getRefreshToken");
        return new ResponseEntity<>(new DataDTO<>(authService.getRefreshToken(request, response).getData().getData()));
    }

    @PostMapping(value = "/save-to-cart")
    public ResponseEntity<DataDTO<AuthUserDTO>> saveProductToCart(@RequestBody CartCreateDTO dto) {
        log.info("Request to User saveProductToCart");
        return service.saveProductToCart(dto);
    }

    @PostMapping(value = "/remove-to-cart/{id}")
    public ResponseEntity<DataDTO<Integer>> removeProductFromCart(@PathVariable Integer id) {
        log.info("Request to Product from User Cart");
        return service.removeProductFromCart(id);
    }

}
