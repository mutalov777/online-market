package uz.mutalov.onlinemarket.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.mutalov.onlinemarket.criteria.OrderCriteria;
import uz.mutalov.onlinemarket.dto.auth.UserDTO;
import uz.mutalov.onlinemarket.dto.order.OrderCreateDTO;
import uz.mutalov.onlinemarket.dto.order.OrderDTO;
import uz.mutalov.onlinemarket.dto.order.OrderUpdateDTO;
import uz.mutalov.onlinemarket.entity.Cart;
import uz.mutalov.onlinemarket.entity.UserOrder;
import uz.mutalov.onlinemarket.exceptions.NotFoundException;
import uz.mutalov.onlinemarket.mappers.OrderMapper;
import uz.mutalov.onlinemarket.repository.AuthUserRepository;
import uz.mutalov.onlinemarket.repository.CartRepository;
import uz.mutalov.onlinemarket.repository.OrderRepository;
import uz.mutalov.onlinemarket.response.DataDTO;
import uz.mutalov.onlinemarket.response.ResponseEntity;
import uz.mutalov.onlinemarket.service.base.AbstractService;
import uz.mutalov.onlinemarket.service.base.GenericCrudService;
import uz.mutalov.onlinemarket.service.base.GenericService;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class OrderService extends AbstractService<OrderRepository, OrderMapper>
        implements GenericCrudService<OrderDTO, OrderCreateDTO, OrderUpdateDTO>,
        GenericService<OrderDTO, OrderCriteria> {
    private final CartRepository cartRepository;
    private final AuthUserRepository authUserRepository;

    public OrderService(OrderRepository repository, @Qualifier("orderMapperImpl") OrderMapper mapper, CartRepository cartRepository, AuthUserRepository authUserRepository) {
        super(repository, mapper);
        this.cartRepository = cartRepository;
        this.authUserRepository = authUserRepository;
    }

    @Override
    public ResponseEntity<DataDTO<OrderDTO>> create(OrderCreateDTO dto) {
        List<Cart> carts = dto.getIds()
                .stream()
                .map(id -> cartRepository
                        .findByIdAndOrderedTrue(id)
                        .orElseThrow(() -> new NotFoundException("Product not found")))
                .toList();
        carts.forEach(cart -> cart.setOrdered(true));

        List<Cart> carts1 = cartRepository.saveAll(carts);

        UserOrder userOrder = new UserOrder(carts1, dto.getTotalPrice());
        UserOrder save = repository.save(userOrder);
        OrderDTO orderDTO = mapper.toDTO(save);
        return new ResponseEntity<>(new DataDTO<>(orderDTO));
    }

    @Override
    public ResponseEntity<DataDTO<OrderDTO>> update(OrderUpdateDTO dto) {
        return null;
    }

    @Override
    public ResponseEntity<DataDTO<Long>> delete(Long id) {
        repository.deleteById(id);
        return new ResponseEntity<>(new DataDTO<>(id));
    }

    @Override
    public ResponseEntity<DataDTO<OrderDTO>> get(Long id) {
        UserOrder userOrder = getOrderById(id);
        OrderDTO orderDTO = mapper.toDTO(userOrder);
        UserDTO userDTO = authUserRepository.findFullNameAndPhoneById(userOrder.getCreatedBy())
                .orElseThrow(() -> new NotFoundException("Owner not found"));
        orderDTO.setOwner(userDTO);
        return new ResponseEntity<>(new DataDTO<>(orderDTO));
    }

    @Override
    public ResponseEntity<DataDTO<List<OrderDTO>>> getAll(OrderCriteria criteria) {
        Sort id =Sort.by(Sort.Direction.ASC,"id");
        PageRequest of = PageRequest.of(criteria.getPage(), criteria.getSize(),id);
        List<UserOrder> content = repository.findAll(of).getContent();
        List<OrderDTO> orderDTOS = mapper.toDTO(content);
        IntStream.range(0, content.size()).forEach(i ->
                orderDTOS
                        .get(i)
                        .setOwner(authUserRepository
                                .findFullNameAndPhoneById(
                                        content
                                                .get(i)
                                                .getCreatedBy())
                                .orElseThrow(() -> new NotFoundException("User not Found"))));
        long count = repository.count();
        return new ResponseEntity<>(new DataDTO<>(orderDTOS, count));
    }

    public ResponseEntity<DataDTO<Long>> delivered(Long id) {
        UserOrder userOrder = getOrderById(id);
        userOrder.setDelivered(true);
        repository.save(userOrder);
        return new ResponseEntity<>(new DataDTO<>(id));
    }

    private UserOrder getOrderById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }
}
