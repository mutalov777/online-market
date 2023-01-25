package uz.mutalov.onlinemarket.entity;

import org.hibernate.annotations.Where;
import uz.mutalov.onlinemarket.entity.base.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Where(clause = "deleted is false")
public class Message extends Auditable {

    @OneToOne(fetch = FetchType.EAGER)
    private AuthUser to;

    @OneToOne(fetch = FetchType.EAGER)
    private AuthUser from;

    private String text;
}
