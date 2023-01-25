package uz.mutalov.onlinemarket.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.mutalov.onlinemarket.criteria.base.AbstractCriteria;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCriteria extends AbstractCriteria {
    private String category;
}
