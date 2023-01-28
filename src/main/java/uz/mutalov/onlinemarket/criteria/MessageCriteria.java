package uz.mutalov.onlinemarket.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.mutalov.onlinemarket.criteria.base.BaseCriteria;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageCriteria implements BaseCriteria {
    private String text;
}
