package uz.mutalov.onlinemarket.dto.admin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Today {
    private Integer orders;
    private Integer delivered;
    private Integer unDelivered;
}
