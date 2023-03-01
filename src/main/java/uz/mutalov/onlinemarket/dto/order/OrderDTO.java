package uz.mutalov.onlinemarket.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.mutalov.onlinemarket.dto.auth.UserDTO;
import uz.mutalov.onlinemarket.dto.base.BaseDTO;
import uz.mutalov.onlinemarket.dto.base.GenericDTO;
import uz.mutalov.onlinemarket.dto.cart.CartDTO;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO extends GenericDTO implements BaseDTO {
    private List<CartDTO> carts;
    private UserDTO owner;
    private Double totalPrice;
    private LocalDateTime createdAt;
    private boolean delivered;
}
