package uz.mutalov.onlinemarket.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import uz.mutalov.onlinemarket.entity.base.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Where(clause = "deleted is false")
public class Product extends Auditable {

    @Column
    private String name;

    @Column
    private Integer price;

    @Column
    private String description;

    @Column
    private Integer count;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductCategory category;
    @Column
    private String photo;

    @Column
    private Boolean isCount;
}

