package uz.mutalov.onlinemarket.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.mutalov.onlinemarket.dto.base.BaseDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageCreateDTO implements BaseDTO {

    private Long to;

    private String text;
}
