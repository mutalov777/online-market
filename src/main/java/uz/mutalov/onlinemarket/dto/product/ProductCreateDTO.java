package uz.mutalov.onlinemarket.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.mutalov.onlinemarket.dto.base.BaseDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDTO implements BaseDTO {
    private String name;
    private Integer price;
    private String description;
    private Integer count;
    private String category;
    private String photo;
    private Boolean isCount;

}
