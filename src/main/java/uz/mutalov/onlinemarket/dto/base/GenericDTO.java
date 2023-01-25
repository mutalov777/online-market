package uz.mutalov.onlinemarket.dto.base;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class GenericDTO implements BaseDTO {
    private Long id;
}
