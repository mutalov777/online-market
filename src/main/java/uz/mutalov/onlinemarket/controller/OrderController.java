package uz.mutalov.onlinemarket.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.mutalov.onlinemarket.controller.base.AbstractController;
import uz.mutalov.onlinemarket.controller.base.GenericController;
import uz.mutalov.onlinemarket.controller.base.GenericCrudController;
import uz.mutalov.onlinemarket.criteria.OrderCriteria;
import uz.mutalov.onlinemarket.dto.order.OrderCreateDTO;
import uz.mutalov.onlinemarket.dto.order.OrderDTO;
import uz.mutalov.onlinemarket.dto.order.OrderUpdateDTO;
import uz.mutalov.onlinemarket.response.DataDTO;
import uz.mutalov.onlinemarket.response.ResponseEntity;
import uz.mutalov.onlinemarket.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController extends AbstractController<OrderService>
        implements GenericCrudController<OrderDTO, OrderCreateDTO, OrderUpdateDTO>,
        GenericController<OrderDTO, OrderCriteria> {
    public OrderController(OrderService service) {
        super(service);
    }

    @Override
    @GetMapping("/get/{id}")
    public ResponseEntity<DataDTO<OrderDTO>> get(@PathVariable Long id) {
        return service.get(id);
    }

    @Override
    @GetMapping("/get-list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DataDTO<List<OrderDTO>>> getAll(OrderCriteria criteria) {
        return service.getAll(criteria);
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<DataDTO<OrderDTO>> create(@RequestBody OrderCreateDTO dto) {
        return service.create(dto);
    }

    @Override
    public ResponseEntity<DataDTO<OrderDTO>> update(@RequestBody OrderUpdateDTO dto) {
        return null;
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DataDTO<Long>> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @PostMapping("/delivered/{id}")
    public ResponseEntity<DataDTO<Long>> delivered(@PathVariable Long id) {
        return service.delivered(id);
    }
}
