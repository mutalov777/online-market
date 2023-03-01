package uz.mutalov.onlinemarket.dto.order;

import uz.mutalov.onlinemarket.dto.base.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDTO implements BaseDTO {
    private List<Integer> ids;
    private Double totalPrice;
}
