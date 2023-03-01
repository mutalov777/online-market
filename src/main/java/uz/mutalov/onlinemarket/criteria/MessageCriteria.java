package uz.mutalov.onlinemarket.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.mutalov.onlinemarket.criteria.base.BaseCriteria;

@Getter
@Setter
@NoArgsConstructor
public class MessageCriteria implements BaseCriteria {
    private String text;
}
