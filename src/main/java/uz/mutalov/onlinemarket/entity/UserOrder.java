package uz.mutalov.onlinemarket.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.mutalov.onlinemarket.entity.base.Auditable;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserOrder extends Auditable {
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(inverseJoinColumns = {@JoinColumn(name = "cart_id")})
    @OrderBy("id")
    private List<Cart> carts;
    @Column(columnDefinition = "bool default 'false'")
    private boolean delivered;
    private Double totalPrice;

    public UserOrder(List<Cart> carts, Double totalPrice) {
        this.carts = carts;
        this.totalPrice = totalPrice;
    }
}
