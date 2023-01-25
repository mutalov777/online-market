package uz.mutalov.onlinemarket.criteria.base;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public  abstract class AbstractCriteria implements BaseCriteria{

    protected Integer page;

    protected Integer size=20;

    public AbstractCriteria(Integer size, Integer page) {
        this.size = size;
        this.page = page;
    }

    public AbstractCriteria(Integer page) {
        this.page=page;
    }

}

