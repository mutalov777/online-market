package uz.mutalov.onlinemarket.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.mutalov.onlinemarket.dto.base.BaseDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryUpdateDTO implements BaseDTO {

    private Integer id;
    private String name;

    private String photo;

}
