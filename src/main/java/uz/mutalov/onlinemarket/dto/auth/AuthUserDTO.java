package uz.mutalov.onlinemarket.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.mutalov.onlinemarket.dto.base.GenericDTO;
import uz.mutalov.onlinemarket.dto.cart.CartDTO;

import javax.validation.constraints.Email;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserDTO extends GenericDTO {
    private String fullName;
    @Email
    private String email;
    private String phone;
    private String role;
    private List<CartDTO> carts;
}
