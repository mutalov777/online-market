package uz.mutalov.onlinemarket.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartUpdateDTO {
    private Integer id;
    private Double amount;

    private Boolean checked;
}
