package uz.mutalov.onlinemarket.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.mutalov.onlinemarket.dto.base.GenericDTO;
import uz.mutalov.onlinemarket.dto.product.ProductDTO;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO extends GenericDTO {

    private Double amount;

    private ProductDTO product;

    private Boolean checked;

    private LocalDateTime updatedAt;
}
