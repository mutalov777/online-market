package uz.mutalov.onlinemarket.dto.product;

import uz.mutalov.onlinemarket.dto.base.GenericDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO extends GenericDTO {
    private String name;

    private Integer price;

    private String description;

    private Integer count;

    private String photo;

    private Boolean isCount;
}
