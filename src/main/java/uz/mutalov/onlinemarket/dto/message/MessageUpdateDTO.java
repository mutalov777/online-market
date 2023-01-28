package uz.mutalov.onlinemarket.dto.message;

import uz.mutalov.onlinemarket.dto.base.GenericDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageUpdateDTO extends GenericDTO {
    private String text;
}
